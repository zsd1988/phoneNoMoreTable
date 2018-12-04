package com.qingpu.phone.common.utils;


import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片和字体处理工具
 */
public class ImageFontUtils {

	/**
	 * 按比例缩放图片
	 * @param src
	 * @param w
	 * @param h
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage zoomImage(String src,int w,int h) throws Exception {
		double wr=0,hr=0;
		File srcFile = new File(src);
		if (!srcFile.isFile()) {
			throw new Exception("ImageDeal>>>" + srcFile + " 不是一个图片文件!");
		}
		BufferedImage bufImg = ImageIO.read(srcFile); //读取图片
		BufferedImage image;//设置缩放目标图片模板
		wr=w*1.0/bufImg.getWidth();     //获取缩放比例
		hr=h*1.0 / bufImg.getHeight();
		AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
		image = ato.filter(bufImg, null);
		return image;
	}

	/**
	 * 旋转图片
	 * @param srcImagePath
	 * @param degree
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage rotateImg(String srcImagePath, int degree) throws Exception{
		BufferedImage image = ImageIO.read(new File(srcImagePath));
		return ImageFontUtils.rotateImg(image, degree);
	}

	/**
	 * 获取字体宽高
	 * @param font
	 * @param content
	 * @return
	 */
	public static Size getWordSize(Font font, String content) {
		FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
		int width = 0;
		for (int i = 0; i < content.length(); i++) {
			width += metrics.charWidth(content.charAt(i));
		}
		Size size = new Size(width, metrics.getHeight());
		return size;
	}

	/**
	 * 旋转图片
	 * @param image
	 * @param degree
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage rotateImg(BufferedImage image, int degree) throws IOException {
		int iw = image.getWidth();// 原始图象的宽度
		int ih = image.getHeight();// 原始图象的高度
		int w = 0;
		int h = 0;
		int x = 0;
		int y = 0;
		degree = degree % 360;
		if (degree < 0)
			degree = 360 + degree;// 将角度转换到0-360度之间
		double ang = Math.toRadians(degree);// 将角度转为弧度
		/**
		 * 确定旋转后的图象的高度和宽度
		 */
		if (degree == 180 || degree == 0 || degree == 360) {
			w = iw;
			h = ih;
		} else if (degree == 90 || degree == 270) {
			w = ih;
			h = iw;
		} else {
		// int d = iw + ih;
			double cosVal = Math.abs(Math.cos(ang));
			double sinVal = Math.abs(Math.sin(ang));
			w = (int) (sinVal * ih) + (int) (cosVal * iw);
			h = (int) (sinVal * iw) + (int) (cosVal * ih);
		}
		x = (w / 2) - (iw / 2);// 确定原点坐标
		y = (h / 2) - (ih / 2);
		BufferedImage rotatedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D gs = rotatedImage.createGraphics();
		rotatedImage = gs.getDeviceConfiguration().createCompatibleImage(w, h,
				Transparency.TRANSLUCENT);
		gs.dispose();
		gs = rotatedImage.createGraphics();
		gs.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		AffineTransform at = new AffineTransform();
		at.rotate(ang, w / 2, h / 2);// 旋转图象
		at.translate(x, y);
		AffineTransformOp op = new AffineTransformOp(at,
				AffineTransformOp.TYPE_BICUBIC);
		op.filter(image, rotatedImage);
		image = rotatedImage;
		return image;
	}
}
