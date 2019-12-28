package bfs;
import java.util.LinkedList;
import java.util.Queue;

class State//ˮ��״̬
{
    int glass[] = new int[3];
    int step;
    State parent;
    public void printpath(){
    	if(parent!= null){
    		System.out.println("\t" +parent.glass[0]+parent.glass[1]+parent.glass[2]);
    		parent.printpath();
    	}
    }
}
public class Water
{
    static int v[] = new int[]{8,5,3};//�����ݻ�
    static int e[] = new int[]{4,4,0};//Ŀ������
    static boolean vis[][][] = new boolean[10][10][10];//��¼�Ѿ����ֵ�״̬
    static void BFS()
    {
        State start = new State();
        start.glass[0] = v[0];
        start.glass[1] = 0;
        start.glass[2] = 0;
        start.parent = null;
        Queue <State> que = new LinkedList<State>();//˫������ʵ�ֶ���
        que.add(start);//��β��ӳ�ʼ��Ԫ��
        vis[start.glass[0]][start.glass[1]][start.glass[2]] = true;//��¼״̬
        while(!que.isEmpty())//���в�Ϊ��
        {
            State temp = new State();
            temp = que.poll();//ɾ��ͷԪ�ظ�ֵ��temp
            for(int i = 0; i < 3; i++)//���η����ڽӵ�
            {
                if(temp.glass[i] == 0)//�ж��Ƿ��ܹ���ˮ
                {
                    continue;
                }
                for(int j = 0; j < 3; j++)//i��j�е�ˮ
                {
                    if(j == i || temp.glass[j] == v[j]) //ͬһ�����ӻ�����������Ѿ�����
                    {
                        continue;
                    }
                    
                    State mid = new State();
                    mid.glass[0] = temp.glass[0];
                    mid.glass[1] = temp.glass[1];
                    mid.glass[2] = temp.glass[2];
                    mid.step = temp.step+1;
                    int rest = v[j]-temp.glass[j];
                    if(rest <= mid.glass[i])//�ж��Ƿ�С��Դ���ӵ�����
                    {
                        mid.glass[j] = v[j];
                        mid.glass[i] = mid.glass[i]-rest;
                        if(vis[mid.glass[0]][mid.glass[1]][mid.glass[2]] == false)//�ж��Ƿ��ǵ�һ�γ��ָ�״̬
                        {
                        	mid.parent = temp;
                            if(mid.glass[0] == e[0] && mid.glass[1] == e[1] && mid.glass[2] == e[2])//�ж��Ƿ���������Ҫ��
                            {
                            	System.out.println("�������Ų���");
                            	System.out.println("\t" +mid.glass[0]+mid.glass[1]+mid.glass[2]);
                            	mid.printpath();
                                System.out.println("���Ų�������"+mid.step);
                                return;
                            }
                            
                            que.add(mid);
                            vis[mid.glass[0]][mid.glass[1]][mid.glass[2]] = true;//��¼״̬
                        }
                    }
                    else//Դ����ˮ����
                    {
                        mid.glass[j] = mid.glass[j] + mid.glass[i];
                        mid.glass[i] = 0;
                        if(vis[mid.glass[0]][mid.glass[1]][mid.glass[2]] == false)//�ж��Ƿ��һ�γ���
                        {
                        	mid.parent = temp;
                            if(mid.glass[0] == e[0] && mid.glass[1] == e[1] && mid.glass[2] == e[2])
                            {
                            	System.out.println("�������Ų���");
                            	System.out.println("\t" +mid.glass[0]+mid.glass[1]+mid.glass[2]);
                            	mid.printpath();
                                System.out.println("���Ų�������"+mid.step);
                                return;
                            }
                            que.add(mid);
                            vis[mid.glass[0]][mid.glass[1]][mid.glass[2]] = true;//��¼״̬
                        }
                    }
                }
            }
        }
    }
    
    
    public static void main(String []args)
    {
    	//���δ�����ʹ�
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                for(int k = 0; k < 10; k++)
                {
                    vis[i][j][k] = false;
                }
            }
        }
        BFS();//������ȱ���
    }
}