import java.util.*;

public class GenerateRandom 
{
	int n;int cnt=0;int rcnt;int tcnt;
	ArrayList<q> Q = new ArrayList<q>();
	ArrayList<q> Q1 = new ArrayList<q>();
	int S[][];
	int choices[];
	Integer randomArray[];
	GenerateRandom(int n)
	{
		tcnt=0;
		cnt=0;
		this.n = n;
		S = new int[n][2*n-2];
		choices = new int [2*n-2];
		randomArray = new Integer[2*n-2];
		for(int i = 0;i<randomArray.length;i++)
		{
			randomArray[i] = i;
		}
		Collections.shuffle(Arrays.asList(randomArray));
	}
	GenerateRandom()
	{
		n=0;
	}	
	
	void randomSchedule()
	{
		/*for(int i = n-1;i>=0;i--)
		{
			for(int j = 0;j<(2*n-2);j++)
			{
				q q = new q(-(i+1),j+1);
				Q.add(q);				
			}
		}*/		
		for(int i = 0;i<n;i++)
		{
			for(int j = 0;j<(2*n-2);j++)
			{
				q q = new q(i+1,j+1);
				Q.add(q);				
			}
		}
		/*for(int i = 0;i<(n*(2*n-2));i++)
		{
			System.out.print(Q.get(i).t+","+Q.get(i).w + "  ");
		}*/ 
		while(tcnt<n*(2*n-2))
		{
			tcnt=0;
			cnt=0;
			S = new int[n][2*n-2];
			choices = new int [2*n-2];
			randomArray = new Integer[2*n-2];
			for(int i = 0;i<randomArray.length;i++)
			{
				randomArray[i] = i;
			}
			Collections.shuffle(Arrays.asList(randomArray));
			Q = new ArrayList<q>();
			for(int i = 0;i<n;i++)
			{
				for(int j = 0;j<(2*n-2);j++)
				{
					q q = new q(i+1,j+1);
					Q.add(q);				
				}
			}
			generatedSchedule();
		}
		/*for(int i = 0;i<S.length;i++)
		{
			for(int j = 0;j<S[0].length;j++)
			{
				System.out.print(S[i][j]+" ");
			}
			System.out.println();
		}*/
	}
	
	boolean generatedSchedule()
	{
		if(tcnt >= Q.size())
		{
			return true;
		}
		q q = new q();
		//q prev_q = new q();q = prev_q;
		System.out.println("cnt = "+cnt+", "+tcnt);	
		q = Q.get(cnt++);
		while((q.t == 0 && q.w == 0))
			q = Q.get(cnt++);
		while(S[Math.abs(q.t)-1][q.w-1] !=0)
		{
			q = Q.get(cnt++);
			while(q.t == 0 && q.w == 0)
				q = Q.get(cnt++); 
		}
		//prev_q = q;
		int k = 0;
		int o=0;
		for(int i = 1;i<=n && k<(2*n);i++)
		{
			if((i != q.t) && (i != -q.t))
			{
				choices[k] = i;
				k++;
				choices[k] = -i;
				k++;				
			}
		}
		for(int i =0;i<S.length;i++)
		{
			for(int j =0;j<choices.length;j++)
			{
				if(S[i][q.w-1] == choices[j] || S[i][q.w-1] == -choices[j])
					choices[j] = S.length+1;
			}
		}
		for(int i = 0;i<randomArray.length;i++)
		{
			o = choices[randomArray[i]];
			//o = choices[i];
			if(o == S.length+1)
				continue;
			else
			{
				if(!find(o,q.w,q.t))
				{
					if(q.t <0)
						q.t = q.t * -1;
					S[q.t-1][q.w-1] = o;
					if(o>0)
						S[o-1][q.w-1] = -q.t;
					else
						S[-o-1][q.w-1] = q.t;
					updateQ(q.t,o,q.w);
					if(generatedSchedule())
						return true;
				}
			}
		}
		return false;
	}
	boolean find(int op,int w,int t)
	{
		/*for(int i = 0;i<Q.size();i++)
		{
			if(((Q.get(i).t == op && Q.get(i).w == w) || (Q.get(i).t == -op && Q.get(i).w == w)) && w != 0)
			{
				return true;
			}
		}
		return false;*/
		/*if(w>=2)
			if(S[Math.abs(t)-1][w-2] == -op)
				return true;*/
		for(int i = 0;i<S[0].length;i++)
		{
			if(S[Math.abs(t)-1][i] == op)
				return true;
		}
		return false;
	}
	void updateQ(int t,int o,int w)
	{
		for(int i = 0;i<Q.size();i++)
		{
			if((Q.get(i).t == t && Q.get(i).w == w) || (Q.get(i).t == o && Q.get(i).w == w) || (Q.get(i).t == -o && Q.get(i).w == w))
			{
				Q.set(i,new q(0,0));
				tcnt++;
			}
		}		
	}
	public static void main(String[] args) 
	{
		int n = 8;
		GenerateRandom g = new GenerateRandom(n);
		for(int i = 0;i<g.randomArray.length;i++)
		{
			System.out.print(g.randomArray[i]+" ");
		}
		System.out.println();
		//int S1 [][] = {{2,3,-4,-3,4,2},{-1,4,3,-4,-3,1},{-4,-1,-2,1,2,4},{3,-2,1,2,-1,-3}};
		//int DM[][] = {{0,745,665,929},{745,0,80,337},{665,80,0,380},{929,337,380,0}};
		
		int S1 [][] = {{6,-2,4,3,-5,-4,-3,5,2,-6},{5,1,-3,-6,4,3,6,-4,-1,-5},{-4,5,2,-1,6,-2,1,-6,-5,4},{3,6,-1,-5,-2,1,5,2,-6,-3},{-2,-3,6,4,1,-6,-4,-1,3,2},{-1,-4,-5,2,-3,5,-2,3,4,1}};
		//int S1 [][] = {{6,-2,4,3,-5,-4,-3,5,2,-6},{5,1,-3,-6,6,3,4,-4,-1,-5},{-4,-5,-2,-1,6,2,1,-6,5,4},{3,6,-1,-5,-2,1,5,2,-6,-3},{-2,-3,6,4,1,-6,-4,-1,3,2},{-1,-4,-5,2,-3,5,-2,3,4,1}};
		//int S1 [][] = {{6,-2,2,3,-5,-4,-3,5,4,-6},{5,1,-1,-5,4,3,6,-4,-6,-3},{-4,5,4,-1,6,-2,1,-6,-5,2},{3,6,-3,-6,-2,1,5,2,-1,-5},{-2,-3,6,2,1,-6,-4,-1,3,4},{-1,-4,-5,4,-3,5,-2,3,2,1}};
		//int DM[][] = {{0,745,665,929,605,521},{745,0,80,337,1090,315},{665,80,0,380,1020,257},{929,337,380,0,1380,408},{605,1090,1020,1380,0,1010},{521,315,257,408,1010,0}};
		int DM[][] = {{0,745,665,929,605,521,370,587},{745,0,80,337,1090,315,567,712},{665,80,0,380,1020,257,501,664},{929,337,380,0,1380,408,622,646},{605,1090,1020,1380,0,1010,957,1190},{521,315,257,408,1010,0,253,410},{370,567,501,622,957,253,0,250},{587,712,664,646,1190,410,250,0}};
		g.randomSchedule();
		for(int i=0;i<g.S.length;i++)
		{
			for(int j=0;j<g.S[0].length;j++)
			{
				System.out.print(g.S[i][j]+" ");
			}
			System.out.println();
		}
		CostOfTournament ob = new CostOfTournament();
		int S2 [][] = ob.SA(g.S,DM);
		for(int i=0;i<S2.length;i++)
		{
			for(int j=0;j<S2[0].length;j++)
			{
				System.out.print(S2[i][j]+" ");
			}
			System.out.println();
		}
	}
}
