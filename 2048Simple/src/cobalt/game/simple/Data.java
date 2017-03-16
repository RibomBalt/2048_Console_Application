package cobalt.game.simple;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Data implements Cloneable {
		private Integer[][] map=null;
		private int score=0;
			
		
		public Integer[][] getMap() {
			return map;
		}
		public int getScore() {
			return score;
		}
		
		public Data(Integer[][] map,int score){
			this.map=map;
			this.score=score;
		}
		public Data(){
			this.map=new Integer[4][4];
			//创建新棋盘后，首先会有两次随机添加操作，然后接受指令
			this.addRandom();
			this.addRandom();
			this.score=0;
		}
		//核心：2048规则实现码:实现了一条线上的2048规则
		/**
		 * 2048游戏规则：
		 * 
		 * 开始时棋盘内随机出现两个数字，出现的数字仅可能为2或4

		　　玩家可以选择上下左右四个方向，若棋盘内的数字出现位移或合并，视为有效移动

		　　玩家选择的方向上若有相同的数字则合并，每次有效移动可以同时合并，但不可以连续合并

		　　合并所得的所有新生成数字想加即为该步的有效得分

		　　玩家选择的方向行或列前方有空格则出现位移

		　　每有效移动一步，棋盘的空位(无数字处)随机出现一个数字(依然可能为2或4)

		　　棋盘被数字填满，无法进行有效移动，判负，游戏结束

		　　棋盘上出现2048，判胜，游戏结束

		 * @author Cobalt-YangFan
		 *
		 */
		private Integer[] fourAction(Integer[] a){
			if(a.length!=4){
				try {
					throw new Exception("数组出错");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//一边推，不合并
			for(int i=1;i<4;i++){
				for(int j=0;j<i;j++){
					if(a[i-1-j]==null){
						a[i-1-j]=a[i-j];
						a[i-j]=null;
					}
				}
			}
			//合并,同时移位，这样也避免了【2,2,4,8】型错误
			for(int i=1;i<4;i++){
				if(a[i]==null){
					break;
				}else if(a[i-1].intValue()==a[i].intValue()){
					a[i-1]=a[i].intValue()<<1;
					//加分
					this.score+=a[i-1].intValue();
					//平移后侧
					for(int j=i;j<3;j++){
						a[j]=a[j+1];
					}
					a[3]=null;
				}
			}
			return a;
		}
		public void upAction(){
			for(int i=0;i<4;i++){
				Integer[] a=this.fourAction(new Integer[]{map[i][0],map[i][1],
						map[i][2],map[i][3]});
				map[i][0]=a[0];
				map[i][1]=a[1];
				map[i][2]=a[2];
				map[i][3]=a[3];
			}
		}
		public void downAction(){
			for(int i=0;i<4;i++){
				Integer[] a=this.fourAction(new Integer[]{map[i][3],map[i][2],
						map[i][1],map[i][0]});
				map[i][3]=a[0];
				map[i][2]=a[1];
				map[i][1]=a[2];
				map[i][0]=a[3];
			}
		}
		public void leftAction(){
			for(int i=0;i<4;i++){
				Integer[] a=this.fourAction(new Integer[]{map[0][i],map[1][i],
						map[2][i],map[3][i]});
				map[0][i]=a[0];
				map[1][i]=a[1];
				map[2][i]=a[2];
				map[3][i]=a[3];
			}
		}
		public void rightAction(){
			for(int i=0;i<4;i++){
				Integer[] a=this.fourAction(new Integer[]{map[3][i],map[2][i],
						map[1][i],map[0][i]});
				map[3][i]=a[0];
				map[2][i]=a[1];
				map[1][i]=a[2];
				map[0][i]=a[3];
			}
		}
		//核心：2048随机系统实现码:实现了操作后自动添加一个数字
		public void addRandom(){
			ArrayList<Integer> nullArea=new ArrayList<Integer>();
			//遍历map，找到null，将序号编码存储
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(this.map[i][j]==null){
						nullArea.add(i*4+j);
					}
				}
			}
			//随机一个整数，保证取到任意null序号的可能性相同
			int random=((int)(Math.random()*(double)nullArea.size()));
			//解码后，赋值，结束
			int set=nullArea.get(random%nullArea.size());
			this.map[set/4][set%4]=(Math.random()<0.5)?2:4;
		}
		//退出存档时调用，将包含棋盘状态和分数的数据.\2048Data.sav文件
		public void save(){
			File sav=new File(".\\2048Data.sav");
			FileOutputStream fos=null;
			if(sav!=null){
				try {
					sav.createNewFile();
					fos=new FileOutputStream(sav);
					for(int i=0;i<4;i++){
						for(int j=0;j<4;j++){
							if(map[i][j]==null){
								fos.write(0);
							}else{
								fos.write(map[i][j]);
							}
						}
					}
					fos.write(score);
					fos.flush();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(fos!=null){
						try {
							fos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}			
		}
		public boolean equals(Data other){
			Integer[][] a=this.getMap();
			Integer[][] b=other.getMap();
			int m=0;
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(a[i][j]==null){
						if(b[i][j]!=null){
							m++;
						}else{
							continue;
						}
					}else{
						if(!a[i][j].equals(b[i][j])){
							m++;
						}else{
							continue;
						}
					}
				}
			}
			return (m==0);
		}
		
		
		public Data clone(){
			Data c=new Data();
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					c.map[i][j]=this.getMap()[i][j];
				}
			}
			c.score=this.getScore();
			return c;
		}
		
		public boolean isLose(){
			Data right=this.clone();
			Data up=this.clone();
			Data down=this.clone();
			Data left=this.clone();
			
			right.rightAction();
			up.upAction();
			down.downAction();
			left.leftAction();
			
			return equals(right)&&equals(left)&&equals(up)&&equals(down);
		}
	}
