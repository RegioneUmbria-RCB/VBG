namespace PersonalLib2.Data
{
	/// <summary>
	/// Le join di tipo SqlServerAndOracle9_Join utilizzano la sintassi: "left outer join"
	/// "right outer join", "inner join"
	/// 
	/// Le join di tipo Oracle8iAndLess_Join utilizzano la sintassi table1.field1(+)=table2.field1
	/// </summary>
	public enum JoinType
	{
		Oracle8iAndLess_Join,
		SqlServerAndOracle9_Join
	}
}