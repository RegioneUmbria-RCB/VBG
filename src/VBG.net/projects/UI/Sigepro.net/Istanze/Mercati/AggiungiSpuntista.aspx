<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="AggiungiSpuntista.aspx.cs" Inherits="Sigepro.net.Istanze.Mercati.AggiungiSpuntista" Title="Rcerca spuntisti" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
<asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
			    <init:LabeledTextBox runat="server" ID="txNominativo" Descrizione="Nominativo" Item-MaxLength="100" Item-Columns="60" />
			    <init:LabeledIntTextBox runat="server" ID="txPresenze" Descrizione="Numero presenze" Item-MaxLength="5" Item-Columns="3" />
			    <div class="Bottoni">
			        <init:SigeproButton runat="server" ID="cmdCerca" Text="Cerca" IdRisorsa="CERCA" OnClick="cmdCerca_Click"/>
			    </div>
                <asp:Repeater ID="rptSpuntisti" runat="server">
                    <HeaderTemplate>
	                    <table>
	                        <colgroup width="5%"></colgroup>
	                        <colgroup width="30%"></colgroup>
	                        <colgroup width="65%"></colgroup>
	                        <tr>
                                <th><asp:LinkButton ID="lnkSelectAll" runat="server" OnClick="lnkSelectAll_Click">Sel. tutti</asp:LinkButton></th>
                                <th>Nominativo</th>
                                <th>Presenze</th>
	                        </tr>
                    </HeaderTemplate>
                    <ItemTemplate>
                        <tr>
                            <td><asp:CheckBox ID="chkSpuntista" runat="server"/></td>
                            <td><asp:TextBox ID="txSpuntista" runat="server" Text='<%#DataBinder.Eval(Container.DataItem,"CodiceAnagrafe")%>' Visible="false"></asp:TextBox><%#DataBinder.Eval(Container.DataItem,"Nominativo")%></td>
                            <td><%#DataBinder.Eval(Container.DataItem,"Presenze")%></td>
                        </tr>
                    </ItemTemplate>
                    <FooterTemplate>
                        </table>
                    </FooterTemplate>
                </asp:Repeater>
                <div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdRiportaSupuntisti"  Text="Ok" IdRisorsa="OK" OnClick="cmdRiportaSupuntisti_Click" Visible="False"/>
				</div>
			</fieldset>
        </asp:View>
	</asp:MultiView>
</asp:Content>