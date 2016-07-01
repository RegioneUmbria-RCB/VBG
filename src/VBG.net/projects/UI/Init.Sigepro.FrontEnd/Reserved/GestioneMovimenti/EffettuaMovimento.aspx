<%@ Page Title="Riepilogo scadenza" ClientIDMode="Static" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="EffettuaMovimento.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.EffettuaMovimento" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<style media="all">
		li
		{
			list-style-type:disc;
		}
		ul
		{
			margin-left: 10px;
		}
	</style>

	<div id="contenutoStep">
		<div class="descrizioneStep">
			<asp:Literal runat="server" ID="ltrDescrizioneStep"/>
		</div>

		<div class="inputForm">
			<fieldset>
			
				<div>
					<asp:Label runat="server" ID="Label2" AssociatedControlID="lblMovimento">Attività:</asp:Label>
					<asp:Label runat="server" ID="lblMovimento" Text='<%# DataBinder.Eval(DataSource,"NomeAttivita") %>' />
				</div>
			
				<div>
					<asp:Label runat="server" ID="Label1" AssociatedControlID="lblDataMovimento">Data movimento:</asp:Label>
					<asp:Label runat="server" ID="lblDataMovimento" Text='<%# DataBinder.Eval(DataSource,"DataAttivita", "{0:dd/MM/yyyy}") %>' />
				</div>

				<div id="divRiferimentiProtocollo" runat="server" visible='<%# DataBinder.Eval(DataSource,"Protocollo.DatiPresenti") %>'>
					<div>
						<asp:Label runat="server" ID="Label5" AssociatedControlID="lblProtocolloMovimento">Con protocollo n.:</asp:Label>
						<asp:Label runat="server" ID="lblProtocolloMovimento" Text='<%# DataBinder.Eval(DataSource,"Protocollo.Numero") %>'/>
					</div>
			
					<div>
						<asp:Label runat="server" ID="Label8" AssociatedControlID="lblDataProtocollo">Del:</asp:Label>
						<asp:Label runat="server" ID="lblDataProtocollo"  Text='<%# DataBinder.Eval(DataSource,"Protocollo.Data", "{0:dd/MM/yyyy}") %>'/>
					</div>
				</div>
			
				<div runat="server" id="divProcedimento" visible='<%# DataBinder.Eval( DataSource,"HaProcedimento" ) %>'>
					<asp:Label runat="server" ID="Label4" AssociatedControlID="lblProcedimento">Procedimento:</asp:Label>
					<asp:Label runat="server" ID="lblProcedimento" Text='<%#DataBinder.Eval( DataSource,"Procedimento" )%>'/>
				</div>
				<div runat="server" id="divAmministrazione" visible='<%# DataBinder.Eval( DataSource,"HaAmministrazione" ) %>'>
					<asp:Label runat="server" ID="Label6" AssociatedControlID="lblAmministrazione">Amministrazione:</asp:Label>
					<asp:Label runat="server" ID="lblAmministrazione" Text='<%#DataBinder.Eval( DataSource,"Amministrazione" )%>'/>
				</div>

				<span id="divPubblica" runat="server" visible='<%# DataBinder.Eval( DataSource,"Pubblica" ) %>'>

					<div runat="server" id="divEsito" visible='<%# DataBinder.Eval( DataSource,"PubblicaEsito" ) %>'>
						<asp:Label runat="server" ID="lblCaptionesito" AssociatedControlID="lblEsito">Esito:</asp:Label>
						<asp:Label runat="server" ID="lblEsito"  Text='<%#DataBinder.Eval( DataSource,"Esito" )%>' />
					</div>

					<div runat="server" id="divParere" visible='<%# DataBinder.Eval( DataSource,"PubblicaOggetto" ) %>'>
						<asp:Label runat="server" ID="lblCaptionParere" AssociatedControlID="lblParere">Oggetto:</asp:Label>
						<asp:Label runat="server" ID="lblParere" Text='<%#DataBinder.Eval( DataSource,"Oggetto" )%>' />
					</div>

					<div runat="server" id="divNote">
						<asp:Label runat="server" ID="Label3" AssociatedControlID="lblNote">Note:</asp:Label>
						<asp:TextBox runat="server" ID="lblNote" Columns="80" Rows="5" TextMode="MultiLine" ReadOnly="true"  Text='<%#DataBinder.Eval( DataSource,"Note" )%>' />
					</div>
				</span>
			</fieldset>

			<fieldset runat="server" id="fieldsetSchede" visible='<%# Convert.ToInt32( DataBinder.Eval( DataSource , "SchedeDinamiche.Count" ) ) > 0 %>'>
				<legend>Schede da compilare</legend>
				<div>
					<asp:Repeater runat="server" ID="rptSchedeMovimento"
												 DataSource='<%# DataBinder.Eval( DataSource, "SchedeDinamiche" ) %>'>
					
					<HeaderTemplate>
						<ul>
					</HeaderTemplate>
					
					<ItemTemplate>
						<li>
							<asp:Literal runat="server" ID="ltrNomeScheda" Text='<%# Eval("NomeScheda") %>' />
						</li>
					</ItemTemplate>		

					<FooterTemplate>
						</ul>
					</FooterTemplate>					 
					</asp:Repeater>
				</div>
			</fieldset>


			<fieldset runat="server" id="fieldsetAllegati" visible='<%# Convert.ToInt32(DataBinder.Eval( DataSource , "Allegati.Count" )) > 0 %>'>
				<legend>Allegati del movimento</legend>
				<div>
					<asp:GridView ID="dgAllegatiMovimento" runat="server" 
															GridLines="None" 
															AutoGenerateColumns="False" DataSource='<%# DataBinder.Eval( DataSource, "Allegati" ) %>' >
						<Columns>
							<asp:BoundField HeaderText="Descrizione" DataField="Descrizione" />
							<asp:BoundField HeaderText="Note" DataField="Note" />
							<asp:TemplateField>
								<ItemTemplate>
									<asp:HyperLink runat="server" ID="lnkMostraAllegato" NavigateUrl='<%# DataBinder.Eval( Container.DataItem, "IdAllegato", "~/MostraOggetto.ashx?idComune=" + IdComune + "&CodiceOggetto={0}" ) %>' Target="_blank" Text='Scarica'  />
								</ItemTemplate>
							</asp:TemplateField>
						</Columns>
						<EmptyDataTemplate>
							<div>Nel movimento di origine non sono presenti allegati</div>
						</EmptyDataTemplate>
					</asp:GridView>
				</div>
			</fieldset>

			<fieldset>
				<legend>Si richiede la seguente attività</legend>
				<div>
					"<asp:Label runat="server" ID="lblNomeMovimentoDaEffettuare" Text='<%# DataBinder.Eval(MovimentoDaEffettuare,"NomeAttivita") %>' />"
				</div>

				<div class="bottoni" id="bottoniMovimento">
					<asp:Button runat="server" ID="cmdProcedi" Text="Procedi" OnClick="cmdProcedi_Click" />
				</div>
			</fieldset>
		</div>

	</div>

</asp:Content>
