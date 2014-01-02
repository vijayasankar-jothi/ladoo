package com.droidfactory.ladoo.util;

import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class AndroidUtils {

	private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

	public static Typeface get(Context c, String name) {
		synchronized (cache) {
			if (!cache.containsKey(name)) {
				Typeface t = Typeface.createFromAsset(c.getAssets(), String.format("fonts/Roboto/%s.ttf", name));
				cache.put(name, t);
			}
			return cache.get(name);
		}
	}

	public static void expand(final View v, final float targtetHeight) {
		v.measure(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1 ? (int) targtetHeight : (int) (targtetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};
		a.setInterpolator(new AccelerateDecelerateInterpolator());
		// 1dp/ms
		a.setDuration((int) (targtetHeight / v.getContext().getResources().getDisplayMetrics().density) * 2);
		v.startAnimation(a);
	}

	public static Animation collapse(final View v) {
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
				} else {
					v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};
		a.setInterpolator(new AccelerateDecelerateInterpolator());

		// 1dp/ms
		a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density) * 2);
		v.startAnimation(a);
		return a;
	}

	public static void expand(final View v, final View to_view) {
		v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		final float targtetHeight = to_view.getTop();

		if (targtetHeight == 0) {
			return;
		}

		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				v.getLayoutParams().height = (int) (targtetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};
		a.setInterpolator(new AccelerateDecelerateInterpolator());
		// 1dp/ms
		a.setDuration((int) (targtetHeight / v.getContext().getResources().getDisplayMetrics().density) * 2);
		v.startAnimation(a);
	}

	public static Animation collapse(final View v, final float targtetHeight) {
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				int transforming_height = initialHeight - (int) (initialHeight * interpolatedTime);
				if (interpolatedTime == 1 || transforming_height <= targtetHeight) {
					v.getLayoutParams().height = (int) targtetHeight;
				} else {
					v.getLayoutParams().height = transforming_height;
				}
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};
		a.setInterpolator(new AccelerateDecelerateInterpolator());

		// 1dp/ms
		a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density) * 2);
		v.startAnimation(a);
		return a;
	}

	public static void expand(final View v) {
		v.measure(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		final int targtetHeight = v.getMeasuredHeight();

		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1 ? LayoutParams.WRAP_CONTENT : (int) (targtetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};
		a.setInterpolator(new AccelerateDecelerateInterpolator());
		// 1dp/ms
		a.setDuration((int) (targtetHeight / v.getContext().getResources().getDisplayMetrics().density) * 2);
		v.startAnimation(a);
	}

	public static boolean isJellyBeanCombCompatible() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

}
