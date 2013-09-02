package com.sukohi.lib;

import android.graphics.PointF;
import android.graphics.RectF;

public class CoodinateInRange {

	private PointF checkPoint = new PointF();
	
	public void setCheckXY(float x, float y) {
		
		checkPoint.set(x, y);
		
	}
	
	public boolean inRangeRectangle(RectF rectF) {
		
		return rectF.contains(checkPoint.x, checkPoint.y);
		
	}
	
	public boolean inRangeRectangleCornerRadius(RectF rectF, float rx, float ry) {
		
		float cornerWidth = rx*2;
		float cornerHeight = ry*2;
		
		if(inRangeRectangle(rectF)) {
			
			float[][] cornerXYs = {
					{rectF.left, rectF.top}, 
					{rectF.right-rx, rectF.top}, 
					{rectF.right-rx, rectF.bottom-ry}, 
					{rectF.left, rectF.bottom-ry}
			};
			float[][] ovalXYs = {
					{rectF.left, rectF.top}, 
					{rectF.right-cornerWidth, rectF.top}, 
					{rectF.right-cornerWidth, rectF.bottom-cornerHeight}, 
					{rectF.left, rectF.bottom-cornerHeight}
			};
			
			for(int i = 0 ; i < 4 ; i++) {

				float[] cornerXY = cornerXYs[i];
				float cornerLeft = cornerXY[0];
				float cornerTop = cornerXY[1];
				float cornerRight = cornerXY[0] + rx;
				float cornerBottom = cornerXY[1] + ry;
				
				RectF RectFCorner = new RectF();
				RectFCorner.set(
						cornerLeft, cornerTop, cornerRight, cornerBottom
				);
				
				float[] ovalXY = ovalXYs[i];
				float ovalLeft = ovalXY[0];
				float ovalTop = ovalXY[1];
				float ovalRight = ovalXY[0] + cornerWidth;
				float ovalBottom = ovalXY[1] + cornerHeight;
				
				RectF RectFOval = new RectF();
				RectFOval.set(
						ovalLeft, ovalTop, ovalRight, ovalBottom
				);
				
				if(RectFCorner.contains(checkPoint.x, checkPoint.y) 
						&& !inRangeOval(RectFOval, cornerWidth, cornerHeight)) {
					
					return false;
					
				}
				
			}
			
			return true;
			
		}
		
		return false;
		
	}
	
	public boolean inRangeCircle(RectF rectF, float width) {

		return inRangeOval(rectF, width, width);
		
	}
	
	public boolean inRangeOval(RectF rectF, float width, float height) {
		
		float horizontalRadius = width / 2;
		float verticalRadius = height / 2;
		float checkX = checkPoint.x - rectF.centerX();
		float checkY = checkPoint.y - rectF.centerY();
		double result = Math.pow(checkX, 2) / Math.pow(horizontalRadius, 2) + Math.pow(checkY, 2) / Math.pow(verticalRadius, 2);
		
		if(result <= 1) {
			
			return true;
			
		}
		
		return false;
		
	}
	
}

/***Sample

	float checkX = 100;
	float checkY = 100;
	RectF rectF = new RectF();
	rectF.set(0, 0, 100, 100);
			
	CoodinateInRange coodinateInRange = new CoodinateInRange();
	coodinateInRange.setCheckXY(checkX, checkY);
	
	if(coodinateInRange.inRangeRectangle(rectF)) {	// for Rectangle
		// In range.
	}
	
	if(coodinateInRange.inRangeRectangleCornerRadius(rectF, 120, 120)) {	// for Rectangle which has coner radiuses
		// In range.
	}
	
	float width = rectF.right;
	float height = rectF.bottom;
	
	if(coodinateInRange.inRangeCircle(rectF, width)) {	// for circle
		// In range.
	}
	
	if(coodinateInRange.inRangeOval(rectF, width, height)) {	// for oval
		// In range.
	}
	
***/
