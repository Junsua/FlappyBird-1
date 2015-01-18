package com.iamlishuai.flappybird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MyPanel extends JPanel implements Runnable {

	//С�����
	private Bird bird;	
	
	//�ܵ�����
	private Pipe[] pipe;
	
	//����������
	private PlaySounds player;
	
	//����ͼƬ
	private Image background;	
	private Image ground;
	
	//��Ϸ״̬
	private GameStatus gs;
	private int rp = 1;
	
	//���췽��
	public MyPanel(Bird bird,Pipe[] pipe,GameStatus gs,PlaySounds player){
		
		//��ʼ������
		background = new ImageIcon(getClass().getResource("/Background.png")).getImage();
		ground = new ImageIcon(getClass().getResource("/Ground.png")).getImage();
		
		//��ʼ��С��
		this.bird = bird;
		
		
		//��ʼ������������
		this.player = player;
		
		//��ʼ���ܵ� 
		this.pipe = new Pipe[4];
		for(int i = 0; i < this.pipe.length; i++){
			this.pipe[i] = pipe[i];
		}
		
		this.gs = gs;
		
	}
	

	public void paint(Graphics g){
		super.paint(g);			
		
		if(GameUI.flag == 0){
			bird.reStart();
		}
		for(int i = 0; i < pipe.length; i++){
			if(GameUI.flag == 0){
				pipe[i].reStart();
			}
		}
		//���Ʊ���
		g.drawImage(background,0,0,800,600,null);
		//���ƹܵ� 
		for(int i = 0; i < pipe.length; i++){
			pipe[i].drawSelf(g);
			
			if(pipe[i].isBirdDied(bird)){
				player.setIsPlay(3);
				GameUI.flag = 2;
			};
			
			if(pipe[i].isBirdPass(bird)){
				player.setIsPlay(2);
				GameUI.addScore();
			}
		}
		//���Ʊ���
		g.drawImage(ground, 0, 480, 800, 112, null);
		
		
		//����С��
		bird.drawSelf(g);
		
		gs.drawSelf(g);
	}

	@Override
	public void run() {

		while(true){
			
			long start = System.currentTimeMillis();
			//����Ϸ״̬��Ϊ��Ϸ����ʱ��ֻ�ػ�һ��(Ϊ����ʾ��Ϸ�÷ְ�)
			if(GameUI.flag == 2){
				if(rp == 1){
					repaint();
					rp = 0;
				} 
				continue;
			}
			repaint();
			if(rp == 0) rp = 1;
			long end = System.currentTimeMillis();
			
			//ȷ����Ϸ֡��Ϊ60
			if(end - start < (1000 / 60)){
				try {
					Thread.sleep(1000 / 60 - end + start);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
}
