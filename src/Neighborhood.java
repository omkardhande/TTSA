//import java.util.*;
public class Neighborhood 
{
	int[][] swapHomes(int i,int j,int S[][])
	{
		for(int k = 0;k<S[0].length;k++)
		{
			if(S[i-1][k] == j || S[i-1][k] == -j)
				S[i-1][k] = - S[i-1][k];
			if(S[j-1][k] == i || S[j-1][k] == -i)
				S[j-1][k] = - S[j-1][k];

		}
		return S;
	}
	
	int[][] swapRounds(int rk,int rl,int S[][])
	{
		int temp = 0;
		for(int i = 0;i<S.length;i++)
		{
			temp = S[i][rk-1];
			S[i][rk-1] = S[i][rl-1];
			S[i][rl-1] = temp;
		}
		return S;
	}
	
	int[][] swapTeams(int i,int j,int S[][])
	{
		int tempi = 0;
		int tempj = 0;
		int temp = 0;
		for(int k = 0;k<S[0].length;k++)
		{
			if(S[i-1][k] != j && S[i-1][k] != -j && S[j-1][k] != i && S[j-1][k] != -i)
				{
					tempi = S[i-1][k];
					tempj = S[j-1][k];
					S[i-1][k] = S[j-1][k];
					S[j-1][k] = tempi;
					
					tempi = Math.abs(tempi);
					tempi--;
					tempj = Math.abs(tempj);
					tempj--;
					if(S[tempi][k] * S[tempj][k] < 0)
					{
						temp = S[tempi][k];
						S[tempi][k] = -S[tempj][k];
						S[tempj][k] = -temp;
					}
					else
					{
						temp = S[tempi][k];
						S[tempi][k] = S[tempj][k];
						S[tempj][k] = temp;						
					}
				}
		}
		return S;
	}
	
	int [][] partialSwapRounds(int t,int rk,int rl,int S[][])
	{
		int temp = 0;
		int visited[] = new int[S.length];
		int k = t;
		for(int i=0;i<S.length;i++)
		{
			visited[k-1] = 1;
			k = Math.abs(S[k-1][rk-1]);
			visited[k-1] = 1;
			k = Math.abs(S[k-1][rl-1]);
		}
		k = t;
		for(int i=0;i<S.length;i++)
		{
			visited[k-1] = 1;
			k = Math.abs(S[k-1][rl-1]);
			visited[k-1] = 1;
			k = Math.abs(S[k-1][rk-1]);
		}
		for(int i=0;i<S.length;i++)
		{
			if(visited[i] == 1)
			{
				temp = S[i][rl-1];
				S[i][rl-1] = S[i][rk-1];
				S[i][rk-1] = temp;
			}
		}
		return S;
		/*int tempk = 0;
		int templ = 0;
		temp = S[t-1][rk-1];
		S[t-1][rk-1] = S[t-1][rl-1];
		S[t-1][rl-1] = temp;
		for(int i = 0;i<S.length;i++)
		{
			if((S[i][rk-1] == rl || S[i][rk-1] == -rl) && (S[i][rl-1] == rk || S[i][rl-1] == -rk))
			{
				temp = S[i][rk-1];
				S[i][rk-1] = S[i][rl-1];
				S[i][rl-1] = temp;
			}
		}
		tempk = S[t-1][rk-1];
		tempk = Math.abs(tempk);
		tempk--;
		templ = S[t-1][rl-1];
		templ = Math.abs(templ);
		templ--;
		
		temp = S[tempk][rk-1];
		S[tempk][rk-1] = S[tempk][rl-1];
		S[tempk][rl-1] = temp;
		
		temp = S[templ][rl-1];
		S[templ][rl-1] = S[templ][rk-1];
		S[templ][rk-1] = temp;
		return S;*/
	}
	
	int [][] partialSwapTeams(int i,int j,int r,int S[][])
	{
		int temp = 0;
		return S;
	}
}
