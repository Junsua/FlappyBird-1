package com.iamlishuai.flappybird;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

/*
 *  С����
 *  ӵ���������塢��������
 */

public class Bird extends Thread {

	// ���λ��
	private int x;
	private int y;
	// ���ֳ�ʼλ��
	private int oldx;
	private int oldy;
	private int ooldx;
	private int ooldy;
	// ���ͼƬ
	private Image[] bird = {
			new ImageIcon(getClass().getResource("/Birds_01.png")).getImage(),
			new ImageIcon(getClass().getResource("/Birds_02.png")).getImage(),
			new ImageIcon(getClass().getResource("/Birds_03.png")).getImage() };
	
	private int imageIndex = 0;
	// ��������ٶ� ���������ٶ�
	private int downv;
	private int upv;
	private int upvn = 0;
	// �������Ĵ�С
	private int up;
	// �½�������
	private static int DOWN = 0;
	private static int FLY = 1;
	// �½��������ı�־
	private int flag = Bird.FLY;

	// ����������������ٶ�
	private double g = 0.0003;

	// ���췽��
	public Bird(int x, int y, int downv, int upv, int up) {
		this.x = x;
		this.y = y;
		this.oldx = x;
		this.oldy = y;
		this.downv = downv;
		this.upv = upv;
		this.up = up;
		ooldx = x;
		ooldy = y;
		
		this.upvn = upv;
	}

	// ����ͼƬ����
	public void setImageIndex() {
		if (imageIndex == 2) {
			imageIndex = 0;
		} else {
			imageIndex++;
		}
	}

	// ����С��ͼƬ
	public void drawSelf(Graphics g) {
		
		//�û�����бһ���Ƕȣ����������бЧ��
		Graphics2D g2 = (Graphics2D) g;
		double a = Math.atan((y - ooldy + 0.000001) / 50);
		if(a > Math.atan(2)){
			a = Math.atan(2);
		}else if(a < Math.atan(-2)){
			a = Math.atan(-2);
		}
		if (GameUI.flag == 1) {
			g2.rotate(a, x + 17, y + 17);
		}

		g.drawImage(bird[imageIndex], x, y, bird[imageIndex].getWidth(null),
				bird[imageIndex].getHeight(null), null);
		
		//�����ʷ�����б������
		if (GameUI.flag == 1) {
			g2.rotate(-a, x + 17, y + 17);
		}

	}

	// �л��������½�״̬
	public void setStatus() {
		if (flag == Bird.DOWN) {
			flag = Bird.FLY;
			ooldx = x;
			ooldy = y;
			GameUI.start = System.currentTimeMillis();
		} else {
			flag = Bird.DOWN;
			ooldx = x;
			ooldy = y;
			GameUI.start = System.currentTimeMillis();
			upv = upvn;
		}
	}

	public void setFlyStatus() {
		flag = Bird.FLY;
		ooldx = x;
		ooldy = y;
		GameUI.start = System.currentTimeMillis();
		upv = upvn;
	}


	// ��ȡС���λ��
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// ���¿�ʼ
	public void reStart() {

		x = oldx;
		y = oldy;
		ooldx = x;
		ooldy = y;

	}

	// �˶�����
	public void move() {

		if (GameUI.flag == 0) {
			setImageIndex();
			return;
		} else if (GameUI.flag == 2) {
			return;
		}

		if (flag == Bird.DOWN) {
			long end = System.currentTimeMillis();
		    long t = (end - GameUI.start);
			int oy = (int) (ooldy + 0.5 * g * t * t);
			y = oy;
		} else {
			y--;
			long end = System.currentTimeMillis();
		    long t = (end - GameUI.start);
			upv += 20 * g * t;
			if((upvn - 60 * g * t) <= 0){
				setStatus();
			}
			if(y <= 0){
				y = 0;
			}
			
		}

		// �ı�С���ͼƬ
		setImageIndex();

	}

	// �̣߳�����һֱ�˶�
	public void run() {
		while (true) {
			move();
			try {
				if (flag == Bird.FLY) {
					sleep(upv);
				} else {
					sleep(downv);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
