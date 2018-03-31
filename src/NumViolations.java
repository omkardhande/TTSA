
public class NumViolations 
{
	int S[][];
	int nTeams;
	int nRounds;
	int numViolations(int [][]S)
	{
		this.S = S;
		int nv = 0;
		nTeams = S.length;
		nRounds = S[0].length;
		nv = nvAtmost()+nvNorepeat();
		return nv;
	}
	int nvAtmost()
	{
		int i;
		int j;
		boolean home_away;
		int nv = 0;
		int n = 0;
		for(int k=0;k<nTeams;k++)
		{
			i = 0;
			while(i<nRounds)
			{
				home_away = S[k][i] > 0;
				n = 0;
				j = i;
				
				while(S[k][j] > 0 == home_away)
				{
					n++;
					j++;
					if(j>= nRounds)
						break;
				}
				if(n>3)
					nv++;
				i++;
			}
		}
		return nv;
	}
	int nvNorepeat()
	{
		int nv=0;
		for(int i=0;i<nTeams;i++)
		{
			for(int j=0;j<nRounds-1;j++)
			{
				if(S[i][j] == -S[i][j+1])
					nv++;
			}
		}
		return nv;
	}
}
