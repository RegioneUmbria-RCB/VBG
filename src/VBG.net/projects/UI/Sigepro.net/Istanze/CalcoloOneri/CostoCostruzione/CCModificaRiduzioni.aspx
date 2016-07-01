<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="CCModificaRiduzioni.aspx.cs" Inherits="Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione.CCModificaRiduzioni" Title="Getione variazioni" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<script type="text/javascript">
		function SwapVisibilita( idPnl )
		{
			var el = document.getElementById("pnlImporti" + idPnl);
			
			if ( !el ) return;
			
			//Debug.Write(document.location);
			
			if (el.style.display == "none")
				el.style.display = "";
			else
				el.style.display = "none";		
		}
		
		function SwapVisibilitaNote( btnRef , idPnl )
		{
			var el = document.getElementById("pnlNote" + idPnl);
			
			if ( !el ) return;
			
			//Debug.Write(document.location);
			
			if (el.style.display != "none")
			{
				el.style.display = "none";	
				return;
			}

			el.style.display = "";
			
			var absPos = PosizioneAssoluta(btnRef);
			
			el.style.position = "absolute";
			el.style.top  = absPos.y + btnRef.height;
			el.style.left = absPos.x - parseInt(el.style.width.replace("px",'')) + btnRef.width;
		}
		
		function ChiudiPannelloNote( idPnl )
		{
			var el = document.getElementById("pnlNote" + idPnl);
			
			if ( !el ) return false;
			
			el.style.display = "none";
			
			return false;
		}
	</script>


	<asp:ScriptManager ID="ScriptManager1" runat="server">
	</asp:ScriptManager>
	<asp:Repeater runat="server" ID="rptTipiCausali" OnItemDataBound="rptTipiCausali_ItemDataBound">
		<HeaderTemplate>
		</HeaderTemplate>
		<ItemTemplate>
			<div class="IntestazioneTabella" style="width: 98%">
				<%#DataBinder.Eval(Container.DataItem , "Descrizione") %>
			</div>
			
			<asp:DataGrid runat="server" ID="dgCausali"  DataKeyField="idCausale" AutoGenerateColumns="false" >
			<ItemStyle CssClass="Riga" />
			<HeaderStyle HorizontalAlign="Center" Font-Bold="true" />
				<Columns>
					<asp:BoundColumn DataField="Descrizione" HeaderText="Causale" ItemStyle-Width="150px"></asp:BoundColumn>
					<asp:BoundColumn DataField="ImportoCausale" HeaderText="Incremento/Riduzione" ItemStyle-Width="190px" ItemStyle-HorizontalAlign="Right" DataFormatString="{0:N2} %"></asp:BoundColumn>
					
					<asp:TemplateColumn  >
						<ItemTemplate>
							<asp:CheckBox runat="server" ID="chkSelezionato" Checked='<%#DataBinder.Eval( Container.DataItem , "Selezionato" ) %>' onclick='<%# DataBinder.Eval(Container.DataItem , "IdCausale" , "SwapVisibilita({0})" ) %>' />
						</ItemTemplate>
					</asp:TemplateColumn>
					
					<asp:TemplateColumn  HeaderText="Applica a percentuale contributo" ItemStyle-HorizontalAlign="Left">
						<ItemTemplate>
							<div id='pnlImporti<%# DataBinder.Eval( Container.DataItem , "IdCausale" ) %>' style='display:<%# (bool)DataBinder.Eval( Container.DataItem , "Selezionato" ) ? "inline" : "none"%>'>
							<init:DoubleTextBox runat="server" ID="dtbImporto" Text='<%#DataBinder.Eval( Container.DataItem , "Importo" ) %>' Columns="6" MaxLength="7"  />
							<asp:ImageButton runat="server" ID="lnkEdit" ImageUrl="~/Images/edit.gif" OnClientClick='<%# DataBinder.Eval(Container.DataItem , "IdCausale" , "SwapVisibilitaNote(this,{0});return false;" ) %>' />
							<div id='pnlNote<%# DataBinder.Eval( Container.DataItem , "IdCausale" ) %>' style="background-color:#fff;display:none;width:400px;border:1px solid #666;position:absolute">
								<div style="width:100%;font-weight:bold;text-align:left">Note della causale <%# DataBinder.Eval( Container.DataItem , "Descrizione" ) %></div>
								<asp:TextBox runat="server" ID="txtNoteImporto" TextMode="MultiLine" Columns="74" Rows="5" Text='<%# DataBinder.Eval( Container.DataItem , "Note" ) %>'></asp:TextBox>
								<init:SigeproButton ID="cmdSalvaNote" runat="server" IdRisorsa="OK" OnClick="cmdSalvaNote_Click" OnClientClick='<%# DataBinder.Eval( Container.DataItem , "IdCausale" , "return ChiudiPannelloNote({0});" ) %>' />
							</div>
							</div>
						</ItemTemplate>
					</asp:TemplateColumn>
				</Columns>
			</asp:DataGrid>

			
		</ItemTemplate>
		<FooterTemplate>
		</FooterTemplate>
	</asp:Repeater>
	<asp:UpdatePanel ID="UpdatePanel1" runat="server">
		<ContentTemplate>
			<init:SigeproButton ID="cmdSalva" runat="server" IdRisorsa="SALVA" OnClick="cmdSalva_Click" />
			<init:SigeproButton ID="cmdChiudi" runat="server" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
		</ContentTemplate>
	</asp:UpdatePanel>

</asp:Content>
