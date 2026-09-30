namespace PersonalLib2.Data
{
    /// <summary>
    /// The collection of ADO.NET data providers that are supported by <see cref="DataProviderFactory"/>.
    /// </summary>
    public enum ProviderType
	{
		/// <summary>
		/// The OLE DB (<see cref="System.Data.OleDb"/>) .NET data provider.
		/// </summary>
		OleDb = 0,
		/// <summary>
		/// The SQL Server (<see cref="System.Data.SqlClient"/>) .NET data provider.
		/// </summary>
		SqlClient,
		/// <summary>
		/// The Oracle (<see cref="System.Data.OracleClient"/>) .NET data provider.
		/// </summary>
		OracleClient,
		/// <summary>
		/// The MySql (<see cref="MySql.Data.MySqlClient"/>) .NET data provider.
		/// </summary>
		MySqlClient,
		/// <summary>
		/// The PostGreSQL (<see cref="Npgsql"/>) .NET data provider.
		/// </summary>
		PostGreSQLClient
	} ;
}