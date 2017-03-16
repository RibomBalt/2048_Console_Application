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
			//���������̺����Ȼ������������Ӳ�����Ȼ�����ָ��
			this.addRandom();
			this.addRandom();
			this.score=0;
		}
		//���ģ�2048����ʵ����:ʵ����һ�����ϵ�2048����
		/**
		 * 2048��Ϸ����
		 * 
		 * ��ʼʱ��������������������֣����ֵ����ֽ�����Ϊ2��4

		������ҿ���ѡ�����������ĸ������������ڵ����ֳ���λ�ƻ�ϲ�����Ϊ��Ч�ƶ�

		�������ѡ��ķ�����������ͬ��������ϲ���ÿ����Ч�ƶ�����ͬʱ�ϲ����������������ϲ�

		�����ϲ����õ�����������������Ӽ�Ϊ�ò�����Ч�÷�

		�������ѡ��ķ����л���ǰ���пո������λ��

		����ÿ��Ч�ƶ�һ�������̵Ŀ�λ(�����ִ�)�������һ������(��Ȼ����Ϊ2��4)

		�������̱������������޷�������Ч�ƶ����и�����Ϸ����

		���������ϳ���2048����ʤ����Ϸ����

		 * @author Cobalt-YangFan
		 *
		 */
		private Integer[] fourAction(Integer[] a){
			if(a.length!=4){
				try {
					throw new Exception("�������");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//һ���ƣ����ϲ�
			for(int i=1;i<4;i++){
				for(int j=0;j<i;j++){
					if(a[i-1-j]==null){
						a[i-1-j]=a[i-j];
						a[i-j]=null;
					}
				}
			}
			//�ϲ�,ͬʱ��λ������Ҳ�����ˡ�2,2,4,8���ʹ���
			for(int i=1;i<4;i++){
				if(a[i]==null){
					break;
				}else if(a[i-1].intValue()==a[i].intValue()){
					a[i-1]=a[i].intValue()<<1;
					//�ӷ�
					this.score+=a[i-1].intValue();
					//ƽ�ƺ��
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
		//���ģ�2048���ϵͳʵ����:ʵ���˲������Զ����һ������
		public void addRandom(){
			ArrayList<Integer> nullArea=new ArrayList<Integer>();
			//����map���ҵ�null������ű���洢
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(this.map[i][j]==null){
						nullArea.add(i*4+j);
					}
				}
			}
			//���һ����������֤ȡ������null��ŵĿ�������ͬ
			int random=((int)(Math.random()*(double)nullArea.size()));
			//����󣬸�ֵ������
			int set=nullArea.get(random%nullArea.size());
			this.map[set/4][set%4]=(Math.random()<0.5)?2:4;
		}
		//�˳��浵ʱ���ã�����������״̬�ͷ���������.\2048Data.sav�ļ�
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
