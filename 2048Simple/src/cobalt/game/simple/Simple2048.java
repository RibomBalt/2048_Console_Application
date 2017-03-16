package cobalt.game.simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Simple2048 {
	
	public static void main(String[] args) {
		System.out.println("欢迎进入2048(DOS精简版)游戏！\n——Cobalt\n");
		Data d=read();
		Data old=d.clone();
		Scanner sc=new Scanner(System.in);
		while(true){
			System.out.println("当前积分："+d.getScore());
			System.out.println("当前棋盘：\n");
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					System.out.print(((d.getMap()[j][i]!=null)?d.getMap()[j][i]:"空")+",\t");
				}
				System.out.println();
			}
			old=d.clone();
			//判断是不是死了
			if(d.isLose()){
				System.out.println("现在不能进行有效移动了！你输了！");
				System.out.println("最终积分："+d.getScore()+"\n\n");
				d=new Data();
				continue;
			}
			System.out.println("请按提示进行输入：w:上，s:下，a:左，d:右，\nsave:保存并退出，exit:不保存直接退出，new:建立新游戏，\n按回车确定本次操作");
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
					System.out.println("已建立新游戏");
					continue;
				}
				default:{
					System.out.println("非法输入！请重新按提示输入");
					continue;
				}
			}
			if(d.equals(old)){
				System.out.println("无效输入！请重新按提示输入");
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
			System.out.println("存档不存在！已建立新游戏！");
			return new Data();
		}catch(IOException e){
			System.out.println("存档无法读取！已建立新游戏！");
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
