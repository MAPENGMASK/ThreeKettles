package dfs;
import java.util.LinkedList;
import java.util.ListIterator;

public class Water {
 
	static int count=0; //计数器
    static int CAPACITY[] = new int[]{8, 5, 3};//定义三个水桶所能容纳的最大的水的含量
    static int INIT_STATE[] = new int[]{8, 0, 0};//定义起始时，三个水桶初始的状态
    static int FINAL_STATE[] = new int[]{4,4,0};//定义三个水桶最终的状态
    
    public static class ActionState {//倒水状态类
        int from;//倒出桶
        int to;//倒入桶
        int water;//所倒的水量
    }
 
    public static class BucketState {//静止状态和倒水状态类
        int[] bucketState = new int[3];//用来记录当前各个桶的状态
        int[] currentAction = new int[3];//当前的倒水动作
        
        public void setBucketState(int bucket[]) {
            for (int i = 0; i < 3; i++) {
                this.bucketState[i] = bucket[i];
            }
        }
 
        public void setActionState(int from, int to, int water) {
            this.currentAction[2] = water;
            this.currentAction[0] = from;
            this.currentAction[1] = to;
        }
        
        public BucketState() {//默认构造函数
            setBucketState(INIT_STATE);
        }
        
        public BucketState(int bucket[]) {//数组初始化
        	setBucketState(bucket);
        }
 
        public BucketState(BucketState state) {//同类型初始化
            setBucketState(state.bucketState);
            setActionState(state.currentAction[0], state.currentAction[1], state.currentAction[2]);
        }
 
        public Boolean isBucketEmpty(int Index) {//判断index号桶是否为空
            if (this.bucketState[Index] > 0) {
                return false;
            } else {
                return true;
            }
        }
 
        public Boolean isBucketFull(int bucketIndex) {//判断index号桶是否为满
            if (this.bucketState[bucketIndex] >= CAPACITY[bucketIndex]) {
                return true;
            } else {
                return false;
            }
        }
 
        public Boolean isSameState(BucketState state) {//判断是否是相同状态
            for (int i = 0; i < 3; ++i) {
                if (this.bucketState[i] != state.bucketState[i])
                    return false;
            }
            return true;
        }
 
        public void printState() {//打印
            System.out.println(" from\t" + (this.currentAction[0] + 1) + "\t to\t" + (this.currentAction[1] + 1)+"\t water\t" + this.currentAction[2]);
            for (int i = 0; i < this.bucketState.length; i++) {
                System.out.print(bucketState[i] + " ");
            }
            System.out.println();
        }
 
        public Boolean isFinalState(){//判断是否是最终状态
            return this.isSameState(new BucketState(FINAL_STATE));
        }
 
        public Boolean canTakeDumpActionState(int from,int to){//判断这个倒水动作能不能进行，from桶不能与to桶一致，from桶不为空，to桶不满，
            if( (from != to) && !this.isBucketEmpty(from) && !this.isBucketFull(to) )
            {
                return true;
            }
            return false;
        }
 
        public Boolean dumpWater(int from,int to,BucketState next){//倒水是否合法next记录倒水后的状态
            next.setBucketState(this.bucketState);
            int dump_water = CAPACITY[to] - next.bucketState[to];  //to桶中可以容纳的水
            if(next.bucketState[from] >= dump_water)   //如果from桶中的水的容量大于等于to桶可以容纳的水，to桶加上dump_water，from桶减去dump_water
            {
                next.bucketState[to] += dump_water;
                next.bucketState[from] -= dump_water;
            }
            else  //如果from桶中的水的容量小于to桶可以容纳的水，to桶加上from桶中的水的容量，dump_water等于from桶中的水，from桶中的水置零。
            {
                next.bucketState[to] += next.bucketState[from];
                dump_water = next.bucketState[from];
                next.bucketState[from] = 0;
            }
 
            if(dump_water > 0) /*是一个有效的倒水动作?*/
            {
                next.setActionState( from, to,dump_water);
                return true;
            }
            return false;
        }
    }
 
    public static Boolean isProcessedState(LinkedList<BucketState> states,BucketState state){//避免循环的出现，在已有的路线表中查看是否已经存在
        ListIterator<BucketState> itr= states.listIterator();
        while (itr.hasNext()){
            if(itr.next().isSameState(state)) {
                return true;
            }
        }
        return false;
    }
  
    public static void printResult(LinkedList<BucketState> states){//当到达最终状态出现之后，打印路径
        System.out.println((++count));
        ListIterator<BucketState> itr= states.listIterator();
        while (itr.hasNext()){
            itr.next().printState();
        }
    }
 
    public static void searchState(LinkedList<BucketState> states){//状态树搜索算法
        BucketState current = states.getLast(); //从链表尾部读取
        if(current.isFinalState())        //判断当前状态是不是最终状态，是的话，打印整个states
        {
            printResult(states);
            System.out.println("辗转步数："+states.size());
            System.out.println("-----------------------------------------");
            return;
        }
 
        for(int j = 0; j < 3; ++j)//排列组合6种倒水状态
        {
            for(int i = 0; i < 3; ++i)
            {
            	if(current.canTakeDumpActionState(i, j))//能否倒水
                {
                    BucketState next=new BucketState();
                    if(current.dumpWater(i, j, next) && !isProcessedState(states, next))
                    {
                        states.addLast(next);
                        searchState(states);
                        states.removeLast();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
    	
        LinkedList<BucketState> states = new LinkedList<>();//双向链表
        BucketState init=new BucketState();    
 
        states.addLast(init);     //将初始的状态插入到链表尾部
        searchState(states);        //调用树搜索算法
 
        System.out.println("搜索结束");  
        
    }
}