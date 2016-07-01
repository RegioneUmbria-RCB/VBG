<%@ Page Title="Titolo" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneLocalizzazioni.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneLocalizzazioni" %>

<%@ Register Assembly="Init.Utils" Namespace="Init.Utils.Web.UI" TagPrefix="cc2" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<div class="inputForm">
		
			<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
				<asp:View runat="server" ID="listaView">
					<fieldset>
						<asp:GridView ID="dgStradario" Width="100%" 
										AutoGenerateColumns="False" 
										DataKeyNames="Id" 
										OnRowDeleting="dgStradario_DeleteCommand" 
										GridLines="None"
										runat="server">
							<Columns>
								<asp:TemplateField HeaderText="Indirizzo">
									<ItemTemplate>
										<asp:Literal runat="server" ID="ltrindirizzo" Text='<%# DataBinder.Eval(Container,"DataItem")%>' />
									</ItemTemplate>									
								</asp:TemplateField>

								<asp:TemplateField HeaderText="Altri dati">
									<ItemTemplate>
										<asp:Literal runat="server" ID="ltrAltriDati" Text='<%# FormattaAltriDati(DataBinder.Eval(Container,"DataItem"))%>' />
									</ItemTemplate>									
								</asp:TemplateField>
								<asp:BoundField DataField="Km"  />	
								<asp:BoundField DataField="Note" HeaderText="Note" />
								<asp:TemplateField HeaderText="Coordinate">
									<ItemTemplate>
										<asp:Literal runat="server" Id="txtLongitudine" Text='<%# FormattaCoordinate(DataBinder.Eval(Container,"DataItem"))%>' />
									</ItemTemplate>
								</asp:TemplateField>
								<asp:TemplateField HeaderText="Rif.Cat.">
									<ItemTemplate>
										<asp:Literal runat="server" Id="txtRifCat" Text='<%# Bind("PrimoRiferimentoCatastale") %>' />								
									</ItemTemplate>
								</asp:TemplateField>

								<asp:TemplateField HeaderStyle-Width="70px" ItemStyle-HorizontalAlign="Right">
									<ItemTemplate>
										<asp:LinkButton runat="server" ID="lnk1" CommandName="Delete" Text="Rimuovi" OnClientClick="return confirm('Proseguire con l\'eliminazione?')"></asp:LinkButton>
									</ItemTemplate>
								</asp:TemplateField>
							</Columns>
						</asp:GridView>

					<div class="bottoni">
						<asp:Button runat="server" ID="cmdAddNew" Text="Aggiungi" OnClick="cmdAddNew_Click" />
					</div>
					</fieldset>
				</asp:View>
				<asp:View runat="server" ID="dettaglioView">

					<fieldset>
					<script type="text/javascript">

						require(['jquery', 'app/autocompleteStradario'], function ($, AutocompleteStradario) {

							$(function () {
							    var tipoCatasto = $('.tipoCatasto'),
								    subalterno = $('.subalterno'),
							        comuneLocalizzazione = $('.comuneLocalizzazione'),
							        listaComuni = $('.ddlComuneLocalizzazione > option'),
                                    autocompleteStradario;


							    autocompleteStradario = new AutocompleteStradario({
								    idCampoIndirizzo: '<%= Indirizzo.ClientID %>',
								    idCampoCodiceStradario: '<%= hiddenCodiceStradario.ClientID%>',
								    idCampoComuneLocalizzazione: '<%= ddlComuneLocalizzazione.Item.ClientID%>',
								    serviceUrl: '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteStradario.asmx") %>/AutocomlpeteStradario',
								    idComune: '<%=IdComune %>',
								    codiceComune: '<%=CodiceComune %>'
								});

								tipoCatasto.change(function () {
									var display = $(this).val() == 'F' ? 'block' : 'none';

									subalterno.css('display', display);
								});

								if (tipoCatasto.val() != 'F')
								    subalterno.css('display', 'none');

								if (listaComuni.length < 2) {
								    comuneLocalizzazione.hide();
								}

								comuneLocalizzazione.on('change', function () {
								    autocompleteStradario.svuotaCampi();
								});
							});


						});
				
					</script>
					
					<asp:GridView runat="server" ID="dgIndirizzi" 
								GridLines="None" 
								AutoGenerateColumns="False" 
								DataKeyNames="CODICESTRADARIO" 
								OnSelectedIndexChanged="dgIndirizzi_SelectedIndexChanged" >
						<Columns>
							<asp:TemplateField HeaderText="Via">
								<ItemTemplate>
									<asp:Literal runat="server" ID="ltrDescrizione" Text='<%# Bind("NomeVia") %>' />
								</ItemTemplate>
							</asp:TemplateField>
							<asp:ButtonField CommandName="Select" Text="Seleziona" />
						</Columns>
					</asp:GridView>

					<div>
						<label>&nbsp;</label>
						<span><i>I campi contrassegnati con * sono obbligatori</i></span>
					</div>


                    <cc2:LabeledDropDownList runat="server" ID="ddlComuneLocalizzazione" 
                                             CssClass="comuneLocalizzazione" 
                                             Item-CssClass="ddlComuneLocalizzazione" 
                                             Descrizione="Comune *" 
                                             Item-DataTextField="Comune" 
                                             Item-DataValueField="CodiceComune" />
					
                        
                    <div>
						<asp:HiddenField runat="server" ID="HiddenCodiceCivico" />
						<asp:HiddenField runat="server" ID="HiddenCodiceViario" />
						<asp:HiddenField runat="server" ID="hiddenCodiceStradario" />
						<asp:Label runat="server" ID="lbl1" AssociatedControlID="Indirizzo" Text="Indirizzo *" />
						<asp:TextBox ID="Indirizzo" runat="server" MaxLength="100" Columns="60" />
						<span id="errMsgOuput"></span>
					</div>

					<cc2:LabeledTextBox runat="server" ID="txtCivico" Descrizione="Civico" Item-MaxLength="10" Item-Columns="9" />
					<cc2:LabeledTextBox runat="server" ID="txtEsponente" Descrizione="Esponente" Item-MaxLength="10" Item-Columns="9" />
					<cc2:LabeledDropDownList runat="server" ID="ddlColore" Descrizione="Colore" Item-DataTextField="COLORE" Item-DataValueField="CODICECOLORE" />
					<cc2:LabeledTextBox runat="server" ID="txtScala" Descrizione="Scala" Item-MaxLength="10" Item-Columns="9" />
					<cc2:LabeledTextBox runat="server" ID="txtPiano" Descrizione="Piano" Item-MaxLength="10" Item-Columns="9" />
					<cc2:LabeledTextBox runat="server" ID="txtInterno" Descrizione="Interno" Item-MaxLength="10" Item-Columns="9" />
					<cc2:LabeledTextBox runat="server" ID="txtEsponenteInterno" Descrizione="Esponente interno" Item-MaxLength="10" Item-Columns="9" />
					<cc2:LabeledTextBox runat="server" ID="txtFabbricato" Descrizione="Fabbricato" Item-MaxLength="10" Item-Columns="9" />
					<cc2:LabeledTextBox runat="server" ID="txtKm" Descrizione="Km" Item-MaxLength="10" Item-Columns="9" />
					<cc2:LabeledTextBox runat="server" ID="txtNote" Descrizione="Note" Item-MaxLength="80" Item-Columns="60" Item-Rows="4" Item-TextMode="MultiLine" />
				</fieldset>
					<%if (CoordinateVisibili){ %>
					<fieldset>
						<legend><asp:Literal runat="server" Text="Coordinate" ID="ltrTitoloBloccoCoordinate"/></legend>
						<cc2:LabeledTextBox runat="server" ID="txtLongitudine" Descrizione="Longitudine" Item-MaxLength="50" Item-Columns="9" />
						<cc2:LabeledTextBox runat="server" ID="txtLatitudine" Descrizione="Latitudine" Item-MaxLength="50" Item-Columns="9" />
					</fieldset>
					<%} %>

					<%if (DatiCatastaliVisibili){ %>
					<fieldset>
						<legend>Riferimenti catastali</legend>

						<cc2:LabeledDropDownList runat="server" ID="ddlTipoCatasto" Descrizione="Tipo catasto" Item-CssClass="tipoCatasto" />
						<asp:HiddenField runat="server" ID="txtSezione" />
						<cc2:LabeledTextBox runat="server" ID="txtFoglio" Descrizione="Foglio" Item-MaxLength="10" Item-Columns="7" />
						<cc2:LabeledTextBox runat="server" ID="txtParticella" Descrizione="Particella" Item-MaxLength="10" Item-Columns="7" />
						<cc2:LabeledTextBox runat="server" ID="txtSub" Descrizione="Subalterno" Item-MaxLength="10" Item-Columns="7" CssClass="subalterno" />
					</fieldset>
					<%} %>

					<fieldset>
						<div class="bottoni">
							<asp:Button ID="cmdConfirm" runat="server" Text="Conferma" OnClick="cmdConfirm_Click" />
							<asp:Button ID="cmdCancel" runat="server" Text="Annulla" OnClick="cmdCancel_Click" />
						</div>
					</fieldset>
				</asp:View>
			</asp:MultiView>
		
	</div>
</asp:Content>
