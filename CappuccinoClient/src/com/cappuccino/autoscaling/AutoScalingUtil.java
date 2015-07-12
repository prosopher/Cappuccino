/*
 *  Copyright 2013-2014 Jin-woo Lee
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.cappuccino.autoscaling;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cappuccino.autoscaling.layout.AutoScalingFragmentLayout;
import com.cappuccino.log.Log;

public class AutoScalingUtil {

	public static final int FULL_HD_PX_W = 1080;

	// 75 + 1701 + 144 = 1920 중에서
	// 디자인 영역 (1701) 만 사용한다.
	// 75 : Status bar 영역
	// 1701 : 디자인 영역
	// 144 : Navigation bar 영역
	public static final int FULL_HD_PX_H = 1701;

	public static class ScaleFactor {
		public float x;
		public float y;
		public float min;

		public ScaleFactor(float x, float y) {
			this.x = x;
			this.y = y;

			// 글자 크기 SCALE 할 때 처럼 X 와 Y 값이 모두 변하는 경우에는 작은 값으로 해줘야 한다.
			this.min = Math.min(x, y);
		}

		@Override
		public String toString() {
			return "Scale Factor X ( " + x + " ) / Scale Factor Y ( " + y
					+ " ) / Scale Factor Min ( " + min + " )";
		}
	}

	public static ScaleFactor getScaleFactor(Context context,
			int wantedFullScrPxW, int wantedFullScrPxH, boolean debuggable) {

		// 스크린 정보를 얻기 위한 WindowManager 를 만든다.
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		// 사용자 폰의 스크린 Dpi 를 구한다.
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);

		float actualFullScrPxW = displayMetrics.widthPixels;
		float actualFullScrPxH = displayMetrics.heightPixels
				- getStatusBarHeight(context);

		// float actualFullScrPxW = view.getWidth();
		// float actualFullScrPxH = view.getHeight();

		// scale factor 를 저장
		ScaleFactor sf = new ScaleFactor((float) actualFullScrPxW
				/ wantedFullScrPxW, (float) actualFullScrPxH / wantedFullScrPxH);

		if (debuggable) {
			Log.e();
			Log.e("Wanted 크기 : w ( " + wantedFullScrPxW + " ) / h ( "
					+ wantedFullScrPxH + " )");
			Log.e("Actual 크기 : w ( " + actualFullScrPxW + " ) / h ( "
					+ actualFullScrPxH + " )");
			Log.e(sf);
		}

		return sf;
	}

	// 출처
	// http://mrtn.me/blog/2012/03/17/get-the-height-of-the-status-bar-in-android/
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	public static void nullifyAndScaleViews(ViewGroup root, ScaleFactor sf,
			Typeface typeface, boolean debuggable) {

		// 모든 조상의 left 와 top 마진을 누적한다.
		int sumLeft = 0;
		int sumTop = 0;
		View child = root;
		ViewGroup parent = (ViewGroup) child.getParent();
		while (true) {
			if (parent.getLayoutParams() instanceof WindowManager.LayoutParams) {
				// 최상위면 탈출
				break;
			} else if (parent.getLayoutParams() instanceof MarginLayoutParams) {
				// 최상위가 아니면 마진 누적
				MarginLayoutParams mlpParent = (MarginLayoutParams) parent
						.getLayoutParams();
				sumLeft += mlpParent.leftMargin;
				sumTop += mlpParent.topMargin;

				child = parent;
				parent = (ViewGroup) child.getParent();
			} else {
				throw new IllegalStateException(parent.getLayoutParams()
						.toString());
			}
		}

		if (debuggable) {
			Log.e("마진 누적 Parent ( L T )");
			Log.e("  " + sumLeft + " " + sumTop);
		}

		scaleView(root, sf, sumLeft, sumTop, typeface, debuggable);

		// 현재 뷰는 위에서 원점으로 돌렸으므로 자식뷰만 스케일한다.
		for (int i = 0; i < root.getChildCount(); i++) {
			child = root.getChildAt(i);

			// AutoScalingFragmentLayout 인 경우만 제외하고 SCALE
			if (!(child instanceof AutoScalingFragmentLayout)) {
				scaleViews(child, sf, typeface, debuggable);
			}
		}
	}

	public static void scaleViews(View view, ScaleFactor sf, Typeface typeface,
			boolean debuggable) {

		// 현재 뷰 SCALE
		// 부모에 대한 가정이 없으므로 0, 0 전달
		scaleView(view, sf, 0, 0, typeface, debuggable);

		// 이게 ViewGroup 일 경우, 모든 자식도 recursive 하게 SCALE
		if (view instanceof ViewGroup) {
			ViewGroup root = (ViewGroup) view;
			for (int i = 0; i < root.getChildCount(); i++) {
				View child = root.getChildAt(i);

				// AutoScalingFragmentLayout 인 경우만 제외하고 SCALE
				if (!(child instanceof AutoScalingFragmentLayout)) {
					scaleViews(child, sf, typeface, debuggable);
				}
			}
		}
	}

	public static void scaleView(View view, ScaleFactor sf,
			int parentLeftMargin, int parentTopMargin, Typeface typeface,
			boolean debuggable) {

		LayoutParams lp = view.getLayoutParams();

		if (debuggable) {
			Log.e();
			Log.e(view);
			Log.e("가로 세로");
			Log.e("  " + lp.width + " " + lp.height);
		}

		// 가로 SCALE
		if (lp.width != LayoutParams.MATCH_PARENT
				&& lp.width != LayoutParams.WRAP_CONTENT) {
			// 정해진 길이가 있을 때만 스케일한다.
			lp.width = (int) (lp.width * sf.x + 0.5);
		}

		// 세로 SCALE
		if (lp.height != LayoutParams.MATCH_PARENT
				&& lp.height != LayoutParams.WRAP_CONTENT) {
			// 정해진 길이가 있을 때만 스케일한다.
			lp.height = (int) (lp.height * sf.y + 0.5);
		}

		if (debuggable) {
			Log.e("  " + lp.width + " " + lp.height);
		}

		// 마진 SCALE
		if (lp instanceof ViewGroup.MarginLayoutParams) {

			ViewGroup.MarginLayoutParams mlpChild = (ViewGroup.MarginLayoutParams) lp;

			if (debuggable) {
				Log.e("마진 Parent ( L T )");
				Log.e("  " + parentLeftMargin + " " + parentTopMargin);
				Log.e("마진 Child ( L T R B )");
				Log.e("  " + mlpChild.leftMargin + " " + mlpChild.topMargin
						+ " " + mlpChild.rightMargin + " "
						+ mlpChild.bottomMargin);
			}

			// 이미 스케일된 parent margin 값 이므로 여기서 곱셈하지 않고 바로 뺀다.
			// 다른 곳은 0보다 크게 하기 위해서 반올림을 하지만, 여기서는 0보다 작아져도 되므로 반올림을 하지 않아야 동작한다.
			mlpChild.leftMargin = (int) (mlpChild.leftMargin * sf.x - parentLeftMargin);
			mlpChild.topMargin = (int) (mlpChild.topMargin * sf.y - parentTopMargin);
			mlpChild.rightMargin = (int) (mlpChild.rightMargin * sf.x);
			mlpChild.bottomMargin = (int) (mlpChild.bottomMargin * sf.y);

			if (debuggable) {
				Log.e("  " + mlpChild.leftMargin + " " + mlpChild.topMargin
						+ " " + mlpChild.rightMargin + " "
						+ mlpChild.bottomMargin);
			}
		}

		// 패딩 SCALE

		if (debuggable) {
			Log.e("패딩 ( L T R B )");
			Log.e("  " + view.getPaddingLeft() + " " + view.getPaddingTop()
					+ " " + view.getPaddingRight() + " "
					+ view.getPaddingBottom());
		}
		view.setPadding((int) (view.getPaddingLeft() * sf.x + 0.5),
				(int) (view.getPaddingTop() * sf.y + 0.5),
				(int) (view.getPaddingRight() * sf.x + 0.5),
				(int) (view.getPaddingBottom() * sf.y + 0.5));
		if (debuggable) {
			Log.e("  " + view.getPaddingLeft() + " " + view.getPaddingTop()
					+ " " + view.getPaddingRight() + " "
					+ view.getPaddingBottom());
		}

		// minWidth, minHeight SCALE
		try {
			Field fMinWidth = View.class.getDeclaredField("mMinWidth");
			Field fMinHeight = View.class.getDeclaredField("mMinHeight");
			fMinWidth.setAccessible(true);
			fMinHeight.setAccessible(true);
			Integer minWidth = (Integer) fMinWidth.get(view);
			Integer minHeight = (Integer) fMinHeight.get(view);

			if (debuggable) {
				Log.e("minWidth minHeight");
				Log.e("  " + minWidth + " " + minHeight);
			}
			view.setMinimumWidth((int) (minWidth * sf.x + 0.5));
			view.setMinimumHeight((int) (minHeight * sf.y + 0.5));
			if (debuggable) {
				minWidth = (Integer) fMinWidth.get(view);
				minHeight = (Integer) fMinHeight.get(view);
				Log.e("  " + minWidth + " " + minHeight);
			}
		} catch (NoSuchFieldException e) {
			Log.e(e);
		} catch (IllegalAccessException e) {
			Log.e(e);
		} catch (IllegalArgumentException e) {
			Log.e(e);
		}

		// maxWidth, maxHeight SCALE
		if (view instanceof ImageView) {
			ImageView iv = (ImageView) view;
			try {
				Field fMaxWidth = ImageView.class.getDeclaredField("mMaxWidth");
				Field fMaxHeight = ImageView.class
						.getDeclaredField("mMaxHeight");
				fMaxWidth.setAccessible(true);
				fMaxHeight.setAccessible(true);
				Integer maxWidth = (Integer) fMaxWidth.get(iv);
				Integer maxHeight = (Integer) fMaxHeight.get(iv);

				if (debuggable) {
					Log.e("maxWidth maxHeight");
					Log.e("  " + maxWidth + " " + maxHeight);
				}
				fMaxWidth.set(iv, (int) (maxWidth * sf.x + 0.5));
				fMaxHeight.set(iv, (int) (maxHeight * sf.y + 0.5));
				if (debuggable) {
					maxWidth = (Integer) fMaxWidth.get(iv);
					maxHeight = (Integer) fMaxHeight.get(iv);
					Log.e("  " + maxWidth + " " + maxHeight);
				}
			} catch (NoSuchFieldException e) {
				Log.e(e);
			} catch (IllegalAccessException e) {
				Log.e(e);
			} catch (IllegalArgumentException e) {
				Log.e(e);
			}
		}

		// maxWidth, maxHeight SCALE
		if (view instanceof ProgressBar) {
			ProgressBar pb = (ProgressBar) view;
			try {
				Field fMaxWidth = ProgressBar.class
						.getDeclaredField("mMaxWidth");
				Field fMaxHeight = ProgressBar.class
						.getDeclaredField("mMaxHeight");
				fMaxWidth.setAccessible(true);
				fMaxHeight.setAccessible(true);
				Integer maxWidth = (Integer) fMaxWidth.get(pb);
				Integer maxHeight = (Integer) fMaxHeight.get(pb);

				if (debuggable) {
					Log.e("maxWidth maxHeight");
					Log.e("  " + maxWidth + " " + maxHeight);
				}
				fMaxWidth.set(pb, (int) (maxWidth * sf.x + 0.5));
				fMaxHeight.set(pb, (int) (maxHeight * sf.y + 0.5));
				if (debuggable) {
					maxWidth = (Integer) fMaxWidth.get(pb);
					maxHeight = (Integer) fMaxHeight.get(pb);
					Log.e("  " + maxWidth + " " + maxHeight);
				}
			} catch (NoSuchFieldException e) {
				Log.e(e);
			} catch (IllegalAccessException e) {
				Log.e(e);
			} catch (IllegalArgumentException e) {
				Log.e(e);
			}
		}

		// dividerHeight SCALE
		if (view instanceof ListView) {
			ListView lv = (ListView) view;

			if (debuggable) {
				Log.e("divider height");
				Log.e("  " + lv.getDividerHeight());
			}
			lv.setDividerHeight((int) (lv.getDividerHeight() * sf.y + 0.5));

			if (debuggable) {
				Log.e("  " + lv.getDividerHeight());
			}
		}

		// 글자 크기 SCALE
		if (view instanceof TextView) {
			TextView tv = (TextView) view;

			if (debuggable) {
				Log.e("텍스트 내용");
				Log.e("  " + tv.getText());
				Log.e("텍스트 크기");
				Log.e("  " + tv.getTextSize());
			}
			tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.getTextSize()
					* sf.min + 0.5F);

			if (debuggable) {
				Log.e("  " + tv.getTextSize());
			}

			// 폰트를 변경한다.
			// XXX FontTest 프로젝트에 남아있는 문제점 해결하기
			// xml 에서 typeface 를 설정하지 않고 textStyle 만 설정한 경우
			// TextView.getStyle 로 bold 는 알아낼 수 있지만 italic 은 알 수 없다.
			// 내부적으로 typeface 가 설정되어 있을 때만 getStyle 을 제대로 쓸 수 있도록 구현되어 있는 듯 하다.
			//
			// 하지만, AutoScaling 패키지에서 필요한 기능은 typeface 는 전역적으로 선언하고
			// textStyle 만 개별적으로 입력하는 경우이므로 문제가 발생한다.
			//
			// 따라서, typeface 가 없어도 textStyle 을 추출할 수 있는 방법이 필요하다.
			// 처음에는, TextView 생성자의 attrs 에서 추출하는 typefaceIndex 와 styleIndex 를
			// reflection 으로 접근 시도했는데, 필드가 아니라 지역변수라서 스타일 정보를 받아올 수가 없었다.
			//
			// 가능한 다른 해결책으로 AspectJ 의 AOP 기능으로 매개변수 접근법을 연구해보기
			if (typeface != null) {
				// 주어진 새로운 typeface 가 있을 경우 폰트 변경

				if (debuggable) {
					Log.e(">>>>>>> 폰트 변경 : " + tv.getText());
				}

				Typeface tfOld = tv.getTypeface();
				if (tfOld == null) {
					// typeface 와 textStyle 이 지정되지 않은 경우
					tv.setTypeface(typeface);
				} else {
					// typeface 또는 textStyle 이 지정된 경우
					tv.setTypeface(typeface, tfOld.getStyle());
				}
			}
		}

		// 다 설정하고 set 을 다시 호출해준다.
		view.setLayoutParams(lp);
	}

	public static View inflateAndScale(View convertView, ViewGroup parent,
			int itemResId) {

		// BaseAdapter 의 getView 메소드에서
		// AutoScaling convertView 초기화를 위해서만 사용되어야 한다.

		if (convertView == null) {
			LayoutInflater li = LayoutInflater.from(parent.getContext());

			// Adapter 내에서 뷰를 inflate 할 때는
			// inflate (int resource, ViewGroup root) 대신
			// inflate (int resource, ViewGroup root, boolean attachToRoot)
			// 이 메소드를 사용하고 인자를 false 로 해야 LayoutParams 가 제대로 등록된다.
			convertView = li.inflate(itemResId, parent, false);

			// Auto scaling 했을 경우 아래와 같은 방식으로 뷰가 사용되기 전에 measure 해야한다.
			// 이 과정을 하지 않으면, 처음 만든 뷰들은 scaling 되지 않고,
			// 나중에 재활용 되는 뷰들만 scaling 된 채로 나타난다.
			ViewGroup.LayoutParams lp = convertView.getLayoutParams();
			int widthMeasureSpec = MeasureSpec.makeMeasureSpec(lp.width,
					MeasureSpec.EXACTLY);
			int heightMeasureSpec = MeasureSpec.makeMeasureSpec(lp.height,
					MeasureSpec.EXACTLY);
			convertView.measure(widthMeasureSpec, heightMeasureSpec);
		}

		return convertView;
	}
}
