package bfs;
import java.util.LinkedList;
import java.util.Queue;

class State//水壶状态
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
    static int v[] = new int[]{8,5,3};//杯子容积
    static int e[] = new int[]{4,4,0};//目的容量
    static boolean vis[][][] = new boolean[10][10][10];//记录已经出现的状态
    static void BFS()
    {
        State start = new State();
        start.glass[0] = v[0];
        start.glass[1] = 0;
        start.glass[2] = 0;
        start.parent = null;
        Queue <State> que = new LinkedList<State>();//双向链表实现队列
        que.add(start);//队尾添加初始化元素
        vis[start.glass[0]][start.glass[1]][start.glass[2]] = true;//记录状态
        while(!que.isEmpty())//队列不为空
        {
            State temp = new State();
            temp = que.poll();//删除头元素赋值给temp
            for(int i = 0; i < 3; i++)//依次访问邻接点
            {
                if(temp.glass[i] == 0)//判断是否能够倒水
                {
                    continue;
                }
                for(int j = 0; j < 3; j++)//i向j中倒水
                {
                    if(j == i || temp.glass[j] == v[j]) //同一个杯子或者这个杯子已经满了
                    {
                        continue;
                    }
                    
                    State mid = new State();
                    mid.glass[0] = temp.glass[0];
                    mid.glass[1] = temp.glass[1];
                    mid.glass[2] = temp.glass[2];
                    mid.step = temp.step+1;
                    int rest = v[j]-temp.glass[j];
                    if(rest <= mid.glass[i])//判断是否小于源杯子的容量
                    {
                        mid.glass[j] = v[j];
                        mid.glass[i] = mid.glass[i]-rest;
                        if(vis[mid.glass[0]][mid.glass[1]][mid.glass[2]] == false)//判断是否是第一次出现该状态
                        {
                        	mid.parent = temp;
                            if(mid.glass[0] == e[0] && mid.glass[1] == e[1] && mid.glass[2] == e[2])//判断是否满足最终要求
                            {
                            	System.out.println("倒序最优步骤");
                            	System.out.println("\t" +mid.glass[0]+mid.glass[1]+mid.glass[2]);
                            	mid.printpath();
                                System.out.println("最优步骤数："+mid.step);
                                return;
                            }
                            
                            que.add(mid);
                            vis[mid.glass[0]][mid.glass[1]][mid.glass[2]] = true;//记录状态
                        }
                    }
                    else//源杯子水不够
                    {
                        mid.glass[j] = mid.glass[j] + mid.glass[i];
                        mid.glass[i] = 0;
                        if(vis[mid.glass[0]][mid.glass[1]][mid.glass[2]] == false)//判断是否第一次出现
                        {
                        	mid.parent = temp;
                            if(mid.glass[0] == e[0] && mid.glass[1] == e[1] && mid.glass[2] == e[2])
                            {
                            	System.out.println("倒序最优步骤");
                            	System.out.println("\t" +mid.glass[0]+mid.glass[1]+mid.glass[2]);
                            	mid.printpath();
                                System.out.println("最优步骤数："+mid.step);
                                return;
                            }
                            que.add(mid);
                            vis[mid.glass[0]][mid.glass[1]][mid.glass[2]] = true;//记录状态
                        }
                    }
                }
            }
        }
    }
    
    
    public static void main(String []args)
    {
    	//标记未被访问过
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
        BFS();//广度优先遍历
    }
}