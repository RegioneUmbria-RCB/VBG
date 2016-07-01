<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" EnableEventValidation="false" AutoEventWireup="true"
	Codebehind="GestioneAnagrafiche.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.DatiAnagrafici" Title="Untitled Page" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="cc2" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register TagPrefix="uc1" TagName="DettagliAnagraficaPf" Src="~/Reserved/InserimentoIstanza/DettagliAnagraficaPf.ascx" %>
<%@ Register TagPrefix="uc2" TagName="DettagliAnagraficaPg" Src="~/Reserved/InserimentoIstanza/DettagliAnagraficaPg.ascx" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<asp:ScriptManager runat="server" ID="scriptManager1">
	</asp:ScriptManager>
	<asp:MultiView runat="server" ID="multiview" ActiveViewIndex="0" OnActiveViewChanged="multiview_ActiveViewChanged">
		<asp:View runat="server" ID="listaView">
			<div class="inputForm">
				<fieldset>
					<asp:GridView runat="server" ID="dgRichiedenti" 
												AutoGenerateColumns="False" 
												DataKeyNames="Id" 
												OnRowDeleting="dgRichiedenti_DeleteCommand"
												OnSelectedIndexChanged="dgRichiedenti_SelectedIndexChanged" 
												OnRowEditing="dgRichiedenti_EditCommand"
												OnRowUpdating="dgRichiedenti_UpdateCommand"
												OnRowCancelingEdit="dgRichiedenti_CancelCommand" GridLines="None" >
						<Columns>
							<asp:TemplateField HeaderText="Nominativo">
								<ItemTemplate>
									<asp:Label ID="lblNominativo" runat="server" Text='<%# Eval("Nominativo") %>' />
								</ItemTemplate>
							</asp:TemplateField>
							<asp:TemplateField HeaderText="In qualità di">
								<ItemTemplate>
									<asp:Label ID="lblInQualitaDi" runat="server" Text='<%# Eval("InQualitaDi") %>' />
								</ItemTemplate>
							</asp:TemplateField>
								
							<asp:TemplateField HeaderText="Azienda collegata">
									<ItemTemplate>
										<asp:Label ID="lblAziendaCollegata" runat="server"  Text='<%# Eval("AziendaCollegata") %>'>
									</asp:Label>
								</ItemTemplate>
									
								<EditItemTemplate>
									<asp:DropDownList runat="server" ID="ddlAziendeCollegabili" DataSource='<%# Eval("AziendeCollegabili") %>' DataTextField="Value" DataValueField="Key" />
								</EditItemTemplate>
							</asp:TemplateField>
							<asp:TemplateField ItemStyle-HorizontalAlign="Right">
								<ItemTemplate>
									<asp:LinkButton ID="lnkCollega" runat="server" Text='<%# Eval("TestoLinkCollegaAzienda") %>' CommandName="Edit" Visible='<%#Eval("MostraLinkCollegaAzienda") %>' />
									<asp:LinkButton ID="LinkButton1" runat="server" Text="Modifica" CommandName="Select" />
									<asp:LinkButton ID="LinkButton2" runat="server" Text="Rimuovi" CommandName="Delete" CausesValidation="false" OnClientClick="return confirm('Proseguire con l\'eliminazione?');" />
								</ItemTemplate>
									
								<EditItemTemplate>
									<asp:LinkButton ID="lnkConferma" runat="server" Text="Conferma" CommandName="Update" />
									<asp:LinkButton ID="lnkAnnulla" runat="server" Text="Annulla" CommandName="Cancel" />
								</EditItemTemplate>
							</asp:TemplateField>
						</Columns>
					</asp:GridView>
			
					<div class="bottoni">
						<asp:Button ID="cmdNuovo" runat="server" Text="Aggiungi soggetto" OnClick="cmdNuovo_Click" />
					</div>
				</fieldset>
			</div>
		</asp:View>
		<asp:View runat="server" ID="nuovoView">
			<script type="text/javascript">

				require(['jquery','jquery.ui'], function ($) { 

					function VerificaCodiceFiscalePartitaIvaObserver( ddltipoPersona , txtCodiceFiscale , cmdSubmit , divPopUp) {

						this.messaggioErroreCodiceFiscale = "Il codice fiscale immesso non sembra essere valido per un cittadino italiano. Procedere?";
						this.messaggioErrorePartitaIva = "La prtita iva immessa non sembra essere valida per un'azienda italiana. Procedere?"
						this.codiceTipoSoggettoPersonaFisica = "F";
						this.codiceTipoSoggettoPersonaGiuridica = "G";

						this.verificaCodiceFiscale = verificaCodiceFiscale;
						this.verificaPartitaIva = verificaPartitaIva;
						this.mostraPopUpValidazione = mostraPopUpValidazione;

						this._ddlTipoPersona = ddltipoPersona;
						this._txtCodiceFiscale = txtCodiceFiscale;
						this._cmdSubmit = cmdSubmit;
						this._divPopUp = divPopUp;
						this._validazioneAttiva = true;


						var self = this;

						this._cmdSubmit.click(function (e) {

							if(!self._validazioneAttiva)
								return;

							var tipoSoggetto = self._ddlTipoPersona.val();

							if (tipoSoggetto === self.codiceTipoSoggettoPersonaFisica && !self.verificaCodiceFiscale()) {
								e.preventDefault();
								self.mostraPopUpValidazione(self.messaggioErroreCodiceFiscale);
								return;
							}

							if (tipoSoggetto === self.codiceTipoSoggettoPersonaGiuridica && !self.verificaPartitaIva()) {
								e.preventDefault();
								self.mostraPopUpValidazione(self.messaggioErrorePartitaIva);
								return;
							}

						});

						function verificaCodiceFiscale() {
							var val = self._txtCodiceFiscale.val();
							var pattern = /^[a-zA-Z]{6}\d\d[a-zA-Z]\d\d[a-zA-Z]\d\d\d[a-zA-Z]/;

							if (val.length !== 16)
								return false;

							if (!pattern.test(val))
								return false;

							return true;
						}


						function verificaPartitaIva() {
							var val = self._txtCodiceFiscale.val();
							var pattern = /^[0-9]{11}$/;

							if (val.length === 11 && pattern.test( val ))
								return true;

							if (!verificaCodiceFiscale())
								return false;

							return true;
						}

						function mostraPopUpValidazione(messaggio) {

							self._divPopUp.html( messaggio );

							self._divPopUp.dialog({
								resizable: false,
								title: 'Verifica del codice fiscale / partita iva immesso',
								width:550,
								modal: true,
								buttons: {
									"Verifica i dati immessi": function() {
										$( this ).dialog( "close" );
									},
									"Prosegui": function() {
										$( this ).dialog( "close" );
										self._validazioneAttiva = false;
										self._cmdSubmit.click();
									}
								}
							});
						}
					}

					var verificaObserver = null;

					$(function(){
						var ddlTipoPersona = $('#<%= cmbTipoPersona.ClientID %>');
						var txtCodiceFiscale = $('#<%= txtCodiceFiscale.ClientID %>');
						var cmdSubmit = $('#<%= cmdCercaCf.ClientID %>');
						var divPopUp = $('#popupErrori');

						verificaObserver = new VerificaCodiceFiscalePartitaIvaObserver(ddlTipoPersona, txtCodiceFiscale, cmdSubmit, divPopUp);

						ddlTipoPersona.on('change', function () {
						    var lbl = $('#<%=lblCodiceFiscale.ClientID%>');
						    var val = ddlTipoPersona.val() == "F" ? "Codice fiscale" : "Codice fiscale impresa";
						    lbl.html(val);
						});
					});
				});
			</script>


			<div class="inputForm">
				<fieldset>
					<div>
						<asp:Label runat="server" ID="Label1" AssociatedControlID="cmbTipoPersona" Text="Tipo soggetto"></asp:Label>
						<asp:DropDownList ID="cmbTipoPersona" runat="server">
							<asp:ListItem Value="F">Persona fisica</asp:ListItem>
							<asp:ListItem Value="G">Persona giuridica</asp:ListItem>
						</asp:DropDownList>
					</div>
					<div>
						<asp:Label runat="server" ID="lblCodiceFiscale" AssociatedControlID="txtCodiceFiscale" Text="Codice fiscale"></asp:Label>
						<asp:TextBox runat="server" ID="txtCodiceFiscale" Columns="22" MaxLength="30" />
						
					</div>
					
					<div class="bottoni">
						<asp:Button ID="cmdCercaCf" runat="server" Text="Cerca" OnClick="cmdCercaCf_Click" />
						<asp:Button ID="cmdCancelNew" runat="server" Text="Annulla" OnClick="OnEndEdit" />
					</div>
				</fieldset>
				
				<div id="popupErrori" style="display:none"></div>
			</div>
		</asp:View>
		<asp:View runat="server" ID="personaFisicaView">
			<uc1:DettagliAnagraficaPf runat="server" ID="DettagliPf"></uc1:DettagliAnagraficaPf>
		</asp:View>
		<asp:View runat="server" ID="personaGiuridicaView">
			<uc2:DettagliAnagraficaPg runat="server" ID="DettagliPg"></uc2:DettagliAnagraficaPg>
		</asp:View>
	</asp:MultiView>
</asp:Content>
