<%@ Page Title="Titolo" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneLocalizzazioniSit.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneLocalizzazioniSit" %>

<%@ Register Assembly="Init.Utils" Namespace="Init.Utils.Web.UI" TagPrefix="cc2" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content2" ContentPlaceHolderID="head" runat="server">
	<style>
		.icona-mappa 
		{
			width:20px; 
			height:20px; 
			vertical-align: bottom; 
			margin-bottom: 2px; 
			cursor: pointer;
		}
	</style>

	

</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	
	<script type="text/javascript">
		require(['jquery'], function ($) {

			$('.icona-mappa').each(function (idx, el) {
				if ($(el).data('urlVisualizzazione') === '' || $(el).data('urlVisualizzazione') === undefined){
					$(el).hide();
				}

				el.getUrl = function () {
					var self = $(this),
						sostituzioni = [
							{ph: '$CIVICO$', val: 'civico' },
							{ph: '$ESPONENTE$', val: 'esponente' },
							{ph: '$CODVIARIO$', val: 'codViario' },
							{ph: '$TIPOCATASTO$', val: 'tipoCatasto' },
							{ph: '$FOGLIO$', val: 'foglio' },
							{ph: '$PARTICELLA$', val: 'particella' },
							{ph: '$SUB$', val: 'sub' },
							{ph: '$CODCIVICO$', val: 'codCivico' },
							{ph: '$SEZIONE$', val: 'sezione' },
						],
						url = self.data('urlVisualizzazione');

					for(var i = 0; i < sostituzioni.length; i++) {
						var val = self.data(sostituzioni[i].val) || '';

						url = url.replace( sostituzioni[i].ph, val);
					}

					return url;
				}
			});

			$('.icona-mappa').on('click', function (e) {
				var el = $(this),
					url = this.getUrl(),
					width	= 700,
					height	= 500,
					top		= (screen.height - height) / 2,
					left	= (screen.width - width) / 2,
					feats = "height=" + height + ", width=" + width + ", menubar=no, resizable=yes,scrollbars=yes,status=no,toolbar=no,top=" + top + ", left=" + left,
					w;

				console.log('Url finestra: ', url);
				console.log('Caratteristiche finestra: ', feats);

				w = window.open(url, 'sit_view', feats)
				e.preventDefault();
				
				if (w) {
					w.focus();
				}
			});
		});
	</script>

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
										<img src='<%=ResolveClientUrl("~/images/map-icon-small.png") %>' class="icona-mappa" 
												data-cod-viario='<%# DataBinder.Eval(Container.DataItem,"CodiceViario")%>' 
												data-cod-civico='<%# DataBinder.Eval(Container.DataItem,"CodiceCivico")%>'
												data-civico='<%# DataBinder.Eval(Container.DataItem,"Civico")%>'
												data-esponente='<%# DataBinder.Eval(Container.DataItem,"Esponente")%>'
												data-url-visualizzazione='<%=UrlLocalizzazioneDaIndirizzo %>'
										/>
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
										<img src='<%=ResolveClientUrl("~/images/map-icon-small.png") %>' class="icona-mappa" 
												data-cod-viario='<%# DataBinder.Eval(Container.DataItem,"CodiceViario")%>' 
												data-cod-civico='<%# DataBinder.Eval(Container.DataItem,"CodiceCivico")%>'
												data-civico='<%# DataBinder.Eval(Container.DataItem,"Civico")%>'
												data-esponente='<%# DataBinder.Eval(Container.DataItem,"Esponente")%>'
												data-tipo-catasto='<%# DataBinder.Eval(Container.DataItem,"PrimoRiferimentoCatastale.CodiceTipoCatasto")%>'
												data-foglio='<%# DataBinder.Eval(Container.DataItem,"PrimoRiferimentoCatastale.Foglio")%>'
												data-particella='<%# DataBinder.Eval(Container.DataItem,"PrimoRiferimentoCatastale.Particella")%>'
												data-sub='<%# DataBinder.Eval(Container.DataItem,"PrimoRiferimentoCatastale.Sub")%>'
												data-url-visualizzazione='<%=UrlLocalizzazioneDaMappali %>'
										/>
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


							var baseUrl = '<%= ResolveClientUrl("~/Reserved/InserimentoIstanza/IntegrazioneSit/IntegrazioneSitService.asmx")%>',
								urlListaCampi = baseUrl + '/GetListaCampi?idcomune=<%=IdComune%>&software=<%=Software %>&token=<%=UserAuthenticationResult.Token %>',
								urlValidazione = baseUrl + '/ValidaCampo?idcomune=<%=IdComune%>&software=<%=Software %>&token=<%=UserAuthenticationResult.Token %>',
								campi = {
									indirizzo	: $("#<%= Indirizzo.ClientID %>"),
									civico		: $("[data-campo-sit='Civico'] > div > input"),
									esponente	: $("[data-campo-sit='Esponente'] > div > input"),
									codViario	: $("#<%=hiddenCodViario.ClientID %>"),
									tipoCatasto : $("[data-campo-sit='TipoCatasto'] > div > select"),
									foglio		: $("[data-campo-sit='Foglio'] > div > input"),
									particella	: $("[data-campo-sit='Particella'] > div > input"),
									sub			: $("[data-campo-sit='Sub'] > div > input"),
									codCivico	: $('#<%=hiddenCodCivico.ClientID %>'),
									sezione		: $('#<%=hiddenSezione.ClientID %>')
								},
								campiIdx = [];
								/*
							campiIdx[0] = campi.indirizzo,
							campiIdx[1] = campi.civico,
							campiIdx[2] = campi.indirizzo,
							campiIdx[3] = campi.indirizzo,
							campiIdx[4] = campi.indirizzo,
							campiIdx[5] = campi.indirizzo,
							campiIdx[6] = campi.indirizzo,							
							campiIdx[7] = campi.indirizzo,
							campiIdx[8] = campi.indirizzo,
							*/
							
							function setTipoCatasto(tipoCatasto) {
								campi.tipoCatasto.val(tipoCatasto);
								campi.tipoCatasto.trigger('change');
							}

							function validate(el) {
								
								var campoErrore = el.parent().find('.input-validation');

								console.log('Inizio validazione');

								if (el.val() === '')
									return;

								var onSuccess = function (d) {											
										var data = d.d,
											validatedFields = $('.input-validation');
											
										validatedFields.html('');
										validatedFields.removeClass('error');

										if (data.error) {
											campoErrore.addClass('error');
											campoErrore.html(data.errorDescription);

											console.log('validazione fallita');

											return;
										}

										campi.civico.val( data.civico );
										campi.esponente.val( data.esponente	);
										campi.foglio.val( data.foglio );
										campi.particella.val( data.particella );
										campi.sub.val( data.sub);
										campi.codCivico.val( data.codCivico );
										campi.sezione.val( data.sezione );

										setTipoCatasto(data.tipoCatasto);

										console.log('validazione completata con successo');
									},

									ajaxRequestParams = {
										url: urlValidazione,
										type: "POST",
										contentType: "application/json; charset=utf-8",
										dataType: "json",
										data: valoriCampi(el)
									};

									$.ajax(ajaxRequestParams).done(onSuccess);
							}

							function valoriCampi(el) {
								return JSON.stringify({ 
									nomeCampo:			el.parent().parent().attr('data-campo-sit'),
									codiceStradario:	campi.codViario.val(),
									civico:				campi.civico.val(),
									// codCivico:			campi.codCivico.val(),
									esponente:			campi.esponente.val(),
									tipoCatasto:		campi.tipoCatasto.val(),
									sezione:			'',
									foglio:				campi.foglio.val(),
									particella:			campi.particella.val(),
									sub:				campi.sub.val() || ''											
								});
							}

							function RicercaSit($) {
							
								var serviceUrl = urlListaCampi,							

									bindAutocomplete = function (el) {
									
										el.parent().append("<span class='input-validation' />");

										var jsonSource = function (request, response) {

											var data = valoriCampi(el),

												onSuccess = function (data) {
											
													response($.map(data.d.items, function (item) {
														return { 
															label:	item,
															id:		item,
															value:	item
														};
													}));
												},

												ajaxRequestParams = {
													url: serviceUrl,
													type: "POST",
													contentType: "application/json; charset=utf-8",
													dataType: "json",
													data: data,
													success: onSuccess
												};

											$.ajax(ajaxRequestParams);
										};

										el.autocomplete({
											source: jsonSource,
											search: function () {
												el.removeClass('valid');
											}
										});

										el.on("autocompleteclose", function (event, ui) {
											console.log('Validazione valore selezionato');
											validate(el);
										});
									};

								

								$('.ricerca-sit > input').each( function (i,el) {
									el = $(el);

									bindAutocomplete(el);

									var startAutocomplete = function () {
										var valToSearch = el.val() === '' ? ' ' : el.val();

										el.autocomplete('search',valToSearch);
									};

									el.on('focus', startAutocomplete);
									el.on('click', startAutocomplete);
									el.on('blur', function() {
										
										var el = $(this);
										
										validate(el);										
									});
								});
							}

							function processaUrl(url) {
								url = url.replace( "$CIVICO$", campi.civico.val());		
								url = url.replace( "$ESPONENTE$", campi.esponente.val());	
								url = url.replace( "$CODVIARIO$", campi.codViario.val());		
								url = url.replace( "$TIPOCATASTO$", campi.tipoCatasto.val());	
								url = url.replace( "$FOGLIO$", campi.foglio.val());
								url = url.replace( "$PARTICELLA$", campi.particella.val());		
								url = url.replace( "$SUB$", campi.sub.val());				
								url = url.replace( "$CODCIVICO$", campi.codCivico.val());		
								url = url.replace( "$SEZIONE$", campi.sezione.val());		

								return url;
							}

							function VisualizzazioneMappa(el, url) {
								var a = $('<a />', { 'href': '#'}),
									imgAttributes = {
										'src': '<%=ResolveClientUrl("~/images/map-icon-small.png") %>',
										'style': 'width:20px; height:20px; vertical-align: bottom; margin-bottom: 2px;'
									},
									img = $("<img />", imgAttributes);
								
								img.appendTo(a);
								a.appendTo(el.parent());

								a.on('click', function (e) {
									var width	= 700,
										height	= 500,
										top		= (screen.height - height) / 2,
										left	= (screen.width - width) / 2,
										feats = "height=" + height + ", width=" + width + ", menubar=no, resizable=yes,scrollbars=yes,status=no,toolbar=no,top=" + top + ", left=" + left,
										w = window.open(processaUrl(url), 'sit_view', feats);

									console.log('Caratteristiche finestra: ', feats);

									e.preventDefault();
									w.focus();
									
								});
							}



							$(function () {
								var tipoCatasto = $('.tipoCatasto')
								var subalterno = $('.subalterno');

								tipoCatasto.on('change', function () {
									var display = $(this).val() == 'F' ? 'block' : 'none';

									subalterno.val('');
									subalterno.css('display', display);
								});

								tipoCatasto.trigger('change');

								RicercaSit($);

								<% if (MostraLocalizzazioneDaIndirizzo) {%>
									VisualizzazioneMappa( $("[data-campo-sit='Esponente'] > div > input") , '<%=UrlLocalizzazioneDaIndirizzo%>');
								<% } %>

								<% if (MostraLocalizzazioneDaMappali) {%>
									VisualizzazioneMappa( $("[data-campo-sit='Particella'] > div > input") , '<%=UrlLocalizzazioneDaMappali%>');
								<% } %>
							});

							AutocompleteStradario({
								idCampoIndirizzo: '<%= Indirizzo.ClientID %>',
								idCampoCodiceStradario: '<%= hiddenCodiceStradario.ClientID%>',
								idCampoCodViario: '<%= hiddenCodViario.ClientID%>',
								serviceUrl: '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteStradario.asmx") %>/AutocomlpeteStradario',
								idComune: '<%=IdComune %>',
								codiceComune: '<%=CodiceComune %>'
								
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

					<div>
						<asp:HiddenField runat="server" ID="hiddenCodViario" />
						<asp:HiddenField runat="server" ID="hiddenCodiceStradario" />
						<asp:HiddenField runat="server" ID="hiddenCodCivico" />
						<asp:HiddenField runat="server" ID="hiddenSezione" />
						<asp:Label runat="server" ID="lbl1" AssociatedControlID="Indirizzo" Text="Indirizzo *" />
						<asp:TextBox ID="Indirizzo" runat="server" MaxLength="100" Columns="60" />
						<span id="errMsgOuput"></span>
					</div>

					<cc2:LabeledTextBox runat="server" ID="txtCivico" Descrizione="Civico" Item-MaxLength="10" Item-Columns="9" CssClass="ricerca-sit" data-campo-sit="Civico" />
					<cc2:LabeledTextBox runat="server" ID="txtEsponente" Descrizione="Esponente" Item-MaxLength="10" Item-Columns="9"  CssClass="ricerca-sit" data-campo-sit="Esponente" />
					<cc2:LabeledDropDownList runat="server" ID="ddlColore" Descrizione="Colore" Item-DataTextField="COLORE" Item-DataValueField="CODICECOLORE" CssClass="ricerca-sit" data-campo-sit="Colore"/>
					<cc2:LabeledTextBox runat="server" ID="txtScala" Descrizione="Scala" Item-MaxLength="10" Item-Columns="9"  CssClass="ricerca-sit" data-campo-sit="Scala"/>
					<cc2:LabeledTextBox runat="server" ID="txtPiano" Descrizione="Piano" Item-MaxLength="10" Item-Columns="9"  CssClass="ricerca-sit" data-campo-sit="Piano"/>
					<cc2:LabeledTextBox runat="server" ID="txtInterno" Descrizione="Interno" Item-MaxLength="10" Item-Columns="9" CssClass="ricerca-sit" data-campo-sit="Interno" />
					<cc2:LabeledTextBox runat="server" ID="txtEsponenteInterno" Descrizione="Esponente interno" Item-MaxLength="10" Item-Columns="9" CssClass="ricerca-sit" data-campo-sit="EsponenteInterno"/>
					<cc2:LabeledTextBox runat="server" ID="txtFabbricato" Descrizione="Fabbricato" Item-MaxLength="10" Item-Columns="9" CssClass="ricerca-sit" data-campo-sit="Fabbricato"/>
					<cc2:LabeledTextBox runat="server" ID="txtKm" Descrizione="Km" Item-MaxLength="10" Item-Columns="9" CssClass="ricerca-sit" data-campo-sit="Km"/>
					<cc2:LabeledTextBox runat="server" ID="txtNote" Descrizione="Note" Item-MaxLength="80" Item-Columns="60" Item-Rows="4" Item-TextMode="MultiLine" />

					<cc2:LabeledTextBox runat="server" ID="txtLongitudine" Descrizione="Longitudine" Item-MaxLength="50" Item-Columns="9" CssClass="ricerca-sit" data-campo-sit="Longitudine"/>
					<cc2:LabeledTextBox runat="server" ID="txtLatitudine" Descrizione="Latitudine" Item-MaxLength="50" Item-Columns="9" CssClass="ricerca-sit" data-campo-sit="Latitudine"/>
				</fieldset>
				<fieldset>
					<legend>Riferimenti catastali</legend>

					<cc2:LabeledDropDownList runat="server" ID="ddlTipoCatasto" Descrizione="Tipo catasto" Item-CssClass="tipoCatasto" CssClass="ricerca-sit" data-campo-sit="TipoCatasto"/>
					<cc2:LabeledTextBox runat="server" ID="txtFoglio" Descrizione="Foglio" Item-MaxLength="10" Item-Columns="7" CssClass="ricerca-sit" data-campo-sit="Foglio"/>
					<cc2:LabeledTextBox runat="server" ID="txtParticella" Descrizione="Particella" Item-MaxLength="10" Item-Columns="7" CssClass="ricerca-sit" data-campo-sit="Particella"/>
					<cc2:LabeledTextBox runat="server" ID="txtSub" Descrizione="Subalterno" Item-MaxLength="10" Item-Columns="7" CssClass="subalterno ricerca-sit" data-campo-sit="Sub"/>
				</fieldset>


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

