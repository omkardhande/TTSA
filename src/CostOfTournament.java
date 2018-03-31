import java.util.Arrays;

//import java.io.*;
public class CostOfTournament 
{
	int cost(int S[][],int DM[][])
	{
		int totalCost  = 0;
		int c = 0;
		int nTeams = S.length;
		int nRounds = S[0].length;
		
		for(int i=0;i<nTeams;i++)
		{
			c = 0;
			if(S[i][0] <0)
				c = c + DM[i][Math.abs(S[i][0])-1];
			totalCost = totalCost + c;
			//System.out.print(c+" "+i+"0 ");
		}
		
		for(int i=0;i<nTeams;i++)
		{
			c = 0;
			for(int j=1;j<nRounds;j++)
			{
				if(S[i][j] > 0)
				{
					if(S[i][j-1] < 0)
					{
						c = c + DM[Math.abs(S[i][j-1])-1][i];
					    //System.out.print(DM[Math.abs(S[i][j-1])-1][i]+" "+i+" "+j+" ");
					}
				}
				else
				{
					if(S[i][j-1] < 0)
					{
						c = c + DM[Math.abs(S[i][j-1])-1][Math.abs(S[i][j])-1];
						//System.out.print(DM[Math.abs(S[i][j-1])-1][Math.abs(S[i][j])-1]+" "+i+" "+j+" ");
					}
					else
					{
						c = c + DM[i][Math.abs(S[i][j])-1];
						//System.out.print(DM[i][Math.abs(S[i][j])-1]+" "+i+" "+j+" ");
					}
				}
			}
			totalCost = totalCost + c;
			//System.out.print(totalCost+" ");
		}
		
		for(int i=0;i<nTeams;i++)
		{
			if(S[i][nRounds-1] < 0)
			{
				totalCost = totalCost + DM[Math.abs(S[i][nRounds-1])-1][i];
				//System.out.print(DM[Math.abs(S[i][nRounds-1])-1][i]+" "+i+" "+(nRounds-1)+" ");
			}
		}
		return totalCost;
	}
	
	double sublinearFunction(int v)
	{
		double op = Math.log(v);
		op = Math.sqrt(v) * op;
		op = 1+op/2;
		return op;	
	}
	
	double costObjectiveFunction(double w,int S[][],int DM[][])
	{
		double C = 0;
		NumViolations ob = new NumViolations();
		long costFeasible = cost(S,DM);
		int violations = ob.numViolations(S); 
		if(violations == 0)
			C = costFeasible;
		else
			C = Math.sqrt((costFeasible*costFeasible) + ((w * sublinearFunction(violations)) * (w * sublinearFunction(violations))));
		return C;
	}
	
	int[][] SA(int S[][], int DM[][])
	{
		int bestS[][] = S;
		int newS[][];
		int nTeams = S.length;
		int nRounds = S[0].length;
		int bestFeasible = Integer.MAX_VALUE;
		int bestInfeasible = Integer.MAX_VALUE;
		int nbf = Integer.MAX_VALUE;
		int nbi = Integer.MAX_VALUE;
		int reheat = 0;
		int counter = 0;
		int phase = 0;
		boolean accept = false;
		double tem = 0;
		int i; int j;int rk;int rl;int m;
		int vnew;
		double del;double c1; double c2;
		final double b = 0.9999;
		double T = 400;
		double bestT = T; //DOUBT
		double w = 4000;
		double delta = 1.04;
		double theta = 1.04;
		final int maxR = 10;
		final int maxP = 100;
		final int maxC = 300;
		
		Neighborhood ob = new Neighborhood();
		NumViolations ob1 = new NumViolations();
		while(reheat <= maxR)
		{
			phase = 0;
			while(phase <= maxP)
			{
				counter = 0;
				while(counter <= maxC)
				{
					do
					{
						i = (int)(1+(Math.random()*(nTeams )));
						j = (int)(1+(Math.random()*(nTeams )));
					}while(i == j);
					
					do
					{
						rk = (int)(1+(Math.random()*(nRounds )));
						rl = (int)(1+(Math.random()*(nRounds )));
					}while(rk == rl);
					
					m = (int)(Math.random() * 4);
					//m=3;
					
					//System.out.println(Arrays.deepToString(S));
					int S1[][] = new int [S.length][S[0].length];
					for(int i1=0;i1<S.length;i1++)
					{
						for(int j1=0;j1<S[0].length;j1++)
						{
							S1[i1][j1] = S[i1][j1];
						}
					}
					if(m == 0)
						newS = ob.swapHomes(i, j, S1);
					else if(m == 1)
						newS = ob.swapRounds(rk, rl, S1);
					else if(m == 2)
						newS = ob.swapTeams(i, j, S1);
					else //if(m == 3)
						newS = ob.partialSwapRounds(i,rk,rl, S1);//irkrl
					//else
						//newS = ob.partialSwapTeams(i, j, rk, S1);
					System.out.println(Arrays.deepToString(S1));
					c1 = costObjectiveFunction(w,newS,DM);
					c2 = costObjectiveFunction(w,S,DM);
					del =  c1 - c2;
					vnew = ob1.numViolations(newS);
					vnew = ob1.numViolations(S);
					if(del<0 || ((vnew == 0) && (c1 < bestFeasible)) || ((vnew > 0) && (c1 < bestInfeasible)))
						accept = true;
					else
					{
						tem = Math.pow(2.718, (del/(double)T));
						if(Math.random() < tem)
							accept = true;
						else
							accept = false;
					}
					if(accept)
					{
						S = newS;
						if(vnew == 0)
							nbf = Math.min((int)c1, bestFeasible);
						else
							nbi = Math.min((int)c1, bestInfeasible);
						if(nbf < bestFeasible || nbi < bestInfeasible)
						{
							if((nbf < bestFeasible) && (vnew == 0))
								bestS = S;
							
							reheat = 0;
							counter = 0;
							phase = 0;
							bestT = T;
							bestFeasible = nbf;
							bestInfeasible = nbi;
							
							if(vnew == 0)
								w = w/theta;
							else
								w = w * delta;
						}
						else
						{
							counter++;
						}
					}
				}
				phase++;
				T = T * b;
			}
			reheat++;
			T = 2 * bestT;
		}
		System.out.println("Cost: "+bestFeasible);
		return bestS;
	}
}
