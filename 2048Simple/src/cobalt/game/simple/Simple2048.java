package cobalt.game.simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Simple2048 {
	
	public static void main(String[] args) {
		System.out.println("��ӭ����2048(DOS�����)��Ϸ��\n����Cobalt\n");
		Data d=read();
		Data old=d.clone();
		Scanner sc=new Scanner(System.in);
		while(true){
			System.out.println("��ǰ���֣�"+d.getScore());
			System.out.println("��ǰ���̣�\n");
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					System.out.print(((d.getMap()[j][i]!=null)?d.getMap()[j][i]:"��")+",\t");
				}
				System.out.println();
			}
			old=d.clone();
			//�ж��ǲ�������
			if(d.isLose()){
				System.out.println("���ڲ��ܽ�����Ч�ƶ��ˣ������ˣ�");
				System.out.println("���ջ��֣�"+d.getScore()+"\n\n");
				d=new Data();
				continue;
			}
			System.out.println("�밴��ʾ�������룺w:�ϣ�s:�£�a:��d:�ң�\nsave:���沢�˳���exit:������ֱ���˳���new:��������Ϸ��\n���س�ȷ�����β���");
			String s=sc.nextLine();
			switch(s){
				case "w":{
					d.upAction();
					break;
				}
				case "s":{
					d.downAction();
					break;
				}
				case "a":{
					d.leftAction();
					break;
				}
				case "d":{
					d.rightAction();
					break;
				}
				case "save":{
					d.save();
					sc.close();
					System.exit(0);
				}
				case "exit":{
					sc.close();
					System.exit(0);
				}
				case "new":{
					d=new Data();
					System.out.println("�ѽ�������Ϸ");
					continue;
				}
				default:{
					System.out.println("�Ƿ����룡�����°���ʾ����");
					continue;
				}
			}
			if(d.equals(old)){
				System.out.println("��Ч���룡�����°���ʾ����");
			}else{
				d.addRandom();
			}
		}
	}
	
	public static Data read(){
		File sav=null;
		FileInputStream fis=null;
		try{
			sav=new File(".\\2048Data.sav");
			fis=new FileInputStream(sav);
			Integer[][] map=new Integer[4][4];
			int c=0;
			int score=0;
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if((c=fis.read())==0){
						continue;
					}else{
						map[i][j]=c;
					}
				}
			}
			score=fis.read();
			return new Data(map,score);
		}catch(FileNotFoundException e){
			System.out.println("�浵�����ڣ��ѽ�������Ϸ��");
			return new Data();
		}catch(IOException e){
			System.out.println("�浵�޷���ȡ���ѽ�������Ϸ��");
			return new Data();
		} finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
