<%@ Page Language="C#" AutoEventWireup="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>

	<script language="c#" runat="server">
	public void Page_Load(object sender, EventArgs e)
	{
		Response.Write( "ui culture NumberDecimalSeparator = " + System.Threading.Thread.CurrentThread.CurrentUICulture.NumberFormat.NumberDecimalSeparator + "<br />");
		Response.Write( "ui culture NumberGroupSeparator = " + System.Threading.Thread.CurrentThread.CurrentUICulture.NumberFormat.NumberGroupSeparator + "<br />");

		Response.Write("culture NumberDecimalSeparator = " + System.Threading.Thread.CurrentThread.CurrentCulture.NumberFormat.NumberDecimalSeparator + "<br />");
		Response.Write("culture NumberGroupSeparator = " + System.Threading.Thread.CurrentThread.CurrentCulture.NumberFormat.NumberGroupSeparator + "<br />");
			
	}
	</script>

</head>
<body>
    <form id="form1" runat="server">
    <div>
    
    </div>
    </form>
</body>
</html>
