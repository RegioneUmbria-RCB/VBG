<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" Inherits="Utilita_ConfiguratoreCalcoli_ConfiguratoreCalcoli" Title="Configuratore Calcoli" Codebehind="ConfiguratoreCalcoli.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils.Web"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
	<asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
	<style>
		table {width: 100%}
	</style>
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="listaView" >
			<fieldset>
				<div>
					<init:GridViewEx AllowSorting="false" 
								 runat="server" 
								 ID="gvLista" 
								 AutoGenerateColumns="False" 
								 OnSelectedIndexChanged="gvLista_SelectedIndexChanged" 
								 DatabindOnFirstLoad="True" 
								 DataSourceID="ObjectDataSource1" 
								 DefaultSortDirection="Ascending" 
								 DataKeyNames="Id, Versione" 
								 DefaultSortExpression="Id" >
						<AlternatingRowStyle CssClass="RigaAlternata" />
						<RowStyle CssClass="Riga" />
						<HeaderStyle CssClass="IntestazioneTabella" />
						<EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
						<EmptyDataTemplate>
							<asp:Label ID="Label6" runat="server">Nessun calcolo configurato.</asp:Label>
						</EmptyDataTemplate>
						<Columns>
							<asp:ButtonField DataTextField="Id" HeaderText="Codice" SortExpression="Id"  CommandName="Select"/>						
							<asp:TemplateField HeaderText="Descrizione" SortExpression="Descrizione">
								<itemtemplate>
									<input type="text" data-id='<%# DataBinder.Eval(Container.DataItem,"Id")%>' value='<%# DataBinder.Eval(Container.DataItem,"Descrizione")%>' name="txtDescrizione" style="width: 95%" />
								</itemtemplate>
							</asp:TemplateField>
							<asp:TemplateField HeaderText="Versione" SortExpression="Versione">
								<itemtemplate>
									<asp:Label runat="server"><%# DataBinder.Eval(Container.DataItem,"Versione")%></asp:Label>
									</itemtemplate>
							</asp:TemplateField>
							<asp:TemplateField HeaderText="Azioni">
								<itemtemplate>
									<i class="fa fa-trash"></i>
								</itemtemplate>
							</asp:TemplateField>
						</Columns>
					</init:GridViewEx>
					<asp:ObjectDataSource ID="ObjectDataSource1" runat="server" OldValuesParameterFormatString="original_{0}" SelectMethod="Find" SortParameterName="sortExpression"
						TypeName="Init.SIGePro.Manager.Logic.GestioneCalcoli.ConfigurazioneCalcoliMgr">
						<SelectParameters>
							<asp:QueryStringParameter Name="token" 
													  QueryStringField="Token" 
													  Type="String" />
							<asp:Parameter Name="sortExpression" Type="String" />
						</SelectParameters>
					</asp:ObjectDataSource>
				</div>
				<div class="Bottoni">
                    
					<init:SigeproButton runat="server" ID="cmdNuovo"  Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiLista_Click" />
				</div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="dettaglioView">
				<iframe src="http://devel3/ConfiguratoreCalcoli/Index.html?Token=2206cffc-5c6d-4ad3-ae8a-58d6a883b276&Id=1"></iframe>
		</asp:View>
	</asp:MultiView>
	<script type="text/javascript">
        const params = new Proxy(new URLSearchParams(window.location.search), {
            get: (searchParams, prop) => searchParams.get(prop),
        });

        let token = params.Token;

		let descrizioni = document.querySelectorAll('[name="txtDescrizione"]');
		descrizioni.forEach(descrizione => {
			descrizione.addEventListener('change', async (event) => {
                event.preventDefault();

				let id = event.target.dataset.id;
				let descrizione = event.target.value;

				await aggiornaDescrizione(id, descrizione);
            })
		});

		async function aggiornaDescrizione(id, descrizione) {

			mostraModalCaricamento();

			const response = await fetch('<%= UrlApiConfigurazioneCalcoli%>/' + id, {
				method: 'PUT',
                mode: 'cors',
				cache: 'no-cache',
                headers: {
                    'Content-Type': 'application/json'
				}, 
				body: JSON.stringify({ "descrizione": descrizione })
			});

			let esito = await response.json();

			nascondiModalCaricamento();
        }


    </script>
</asp:Content>