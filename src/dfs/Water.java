package dfs;
import java.util.LinkedList;
import java.util.ListIterator;

public class Water {
 
	static int count=0; //������
    static int CAPACITY[] = new int[]{8, 5, 3};//��������ˮͰ�������ɵ�����ˮ�ĺ���
    static int INIT_STATE[] = new int[]{8, 0, 0};//������ʼʱ������ˮͰ��ʼ��״̬
    static int FINAL_STATE[] = new int[]{4,4,0};//��������ˮͰ���յ�״̬
    
    public static class ActionState {//��ˮ״̬��
        int from;//����Ͱ
        int to;//����Ͱ
        int water;//������ˮ��
    }
 
    public static class BucketState {//��ֹ״̬�͵�ˮ״̬��
        int[] bucketState = new int[3];//������¼��ǰ����Ͱ��״̬
        int[] currentAction = new int[3];//��ǰ�ĵ�ˮ����
        
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
        
        public BucketState() {//Ĭ�Ϲ��캯��
            setBucketState(INIT_STATE);
        }
        
        public BucketState(int bucket[]) {//�����ʼ��
        	setBucketState(bucket);
        }
 
        public BucketState(BucketState state) {//ͬ���ͳ�ʼ��
            setBucketState(state.bucketState);
            setActionState(state.currentAction[0], state.currentAction[1], state.currentAction[2]);
        }
 
        public Boolean isBucketEmpty(int Index) {//�ж�index��Ͱ�Ƿ�Ϊ��
            if (this.bucketState[Index] > 0) {
                return false;
            } else {
                return true;
            }
        }
 
        public Boolean isBucketFull(int bucketIndex) {//�ж�index��Ͱ�Ƿ�Ϊ��
            if (this.bucketState[bucketIndex] >= CAPACITY[bucketIndex]) {
                return true;
            } else {
                return false;
            }
        }
 
        public Boolean isSameState(BucketState state) {//�ж��Ƿ�����ͬ״̬
            for (int i = 0; i < 3; ++i) {
                if (this.bucketState[i] != state.bucketState[i])
                    return false;
            }
            return true;
        }
 
        public void printState() {//��ӡ
            System.out.println(" from\t" + (this.currentAction[0] + 1) + "\t to\t" + (this.currentAction[1] + 1)+"\t water\t" + this.currentAction[2]);
            for (int i = 0; i < this.bucketState.length; i++) {
                System.out.print(bucketState[i] + " ");
            }
            System.out.println();
        }
 
        public Boolean isFinalState(){//�ж��Ƿ�������״̬
            return this.isSameState(new BucketState(FINAL_STATE));
        }
 
        public Boolean canTakeDumpActionState(int from,int to){//�ж������ˮ�����ܲ��ܽ��У�fromͰ������toͰһ�£�fromͰ��Ϊ�գ�toͰ������
            if( (from != to) && !this.isBucketEmpty(from) && !this.isBucketFull(to) )
            {
                return true;
            }
            return false;
        }
 
        public Boolean dumpWater(int from,int to,BucketState next){//��ˮ�Ƿ�Ϸ�next��¼��ˮ���״̬
            next.setBucketState(this.bucketState);
            int dump_water = CAPACITY[to] - next.bucketState[to];  //toͰ�п������ɵ�ˮ
            if(next.bucketState[from] >= dump_water)   //���fromͰ�е�ˮ���������ڵ���toͰ�������ɵ�ˮ��toͰ����dump_water��fromͰ��ȥdump_water
            {
                next.bucketState[to] += dump_water;
                next.bucketState[from] -= dump_water;
            }
            else  //���fromͰ�е�ˮ������С��toͰ�������ɵ�ˮ��toͰ����fromͰ�е�ˮ��������dump_water����fromͰ�е�ˮ��fromͰ�е�ˮ���㡣
            {
                next.bucketState[to] += next.bucketState[from];
                dump_water = next.bucketState[from];
                next.bucketState[from] = 0;
            }
 
            if(dump_water > 0) /*��һ����Ч�ĵ�ˮ����?*/
            {
                next.setActionState( from, to,dump_water);
                return true;
            }
            return false;
        }
    }
 
    public static Boolean isProcessedState(LinkedList<BucketState> states,BucketState state){//����ѭ���ĳ��֣������е�·�߱��в鿴�Ƿ��Ѿ�����
        ListIterator<BucketState> itr= states.listIterator();
        while (itr.hasNext()){
            if(itr.next().isSameState(state)) {
                return true;
            }
        }
        return false;
    }
  
    public static void printResult(LinkedList<BucketState> states){//����������״̬����֮�󣬴�ӡ·��
        System.out.println((++count));
        ListIterator<BucketState> itr= states.listIterator();
        while (itr.hasNext()){
            itr.next().printState();
        }
    }
 
    public static void searchState(LinkedList<BucketState> states){//״̬�������㷨
        BucketState current = states.getLast(); //������β����ȡ
        if(current.isFinalState())        //�жϵ�ǰ״̬�ǲ�������״̬���ǵĻ�����ӡ����states
        {
            printResult(states);
            System.out.println("շת������"+states.size());
            System.out.println("-----------------------------------------");
            return;
        }
 
        for(int j = 0; j < 3; ++j)//�������6�ֵ�ˮ״̬
        {
            for(int i = 0; i < 3; ++i)
            {
            	if(current.canTakeDumpActionState(i, j))//�ܷ�ˮ
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
    	
        LinkedList<BucketState> states = new LinkedList<>();//˫������
        BucketState init=new BucketState();    
 
        states.addLast(init);     //����ʼ��״̬���뵽����β��
        searchState(states);        //�����������㷨
 
        System.out.println("��������");  
        
    }
}