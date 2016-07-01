<%@ Page Title="Invio Istanza" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="InvioIstanza.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.InvioIstanza" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<div class="inputForm">
		<asp:MultiView runat="server" ID="mvInvioIstanza">
			<asp:View runat="server" ID="allegaFileView">

                <fieldset class="firmaRiepilogoDomanda">
					<legend>
						<asp:Literal runat="server" ID="ltrSottotitoloInvio">
							Scaricare, firmare digitalmente e ricaricare il documento
						</asp:Literal>
					</legend>

					<div>
						<asp:Literal runat="server" ID="ltrDescrizioneFaseInvio" />
					</div>

					<!-- File da scaricare e firmare digitalmente -->
					<ul>
						<li>
							<asp:HyperLink runat="server" ID="hlModelloDomanda">
								<asp:Image ID="Image2" runat="server" ImageUrl="~/Images/pdf16x16.gif" /> 
								<asp:Literal runat="server" ID="ltrNomeFiledaScaricare" Text="Istanza" />
							</asp:HyperLink>
						</li>
					</ul>

					<div>
						<asp:Label runat="server" 
									ID="Label4" 
									Text="Selezionare il file da inviare" 
									AssociatedControlID="fuRiepilogo" />
						<asp:FileUpload runat="server" ID="fuRiepilogo" Style="width: 400px" />
					</div>
					<div class="bottoni">
						<asp:Button runat="server" 
									ID="cmdUploadDomanda" 
									Text="Allega" 
									OnClick="cmdUploadDomanda_Click" />
					</div>
                </fieldset>
			</asp:View>

			<asp:View runat="server" ID="firmaEInviaView">
				<style media="all">
					.messaggioInvio
					{
						padding: 10px;
						display:none;
					}
					.messaggioInvio>img
					{
						float: left;
					}
						
					.ui-dialog-titlebar-close
					{
						display:none;
					}			
                    
                    span.checkbox >label {
                        float: none !important;
                        margin-left: 10px;
                    }

				    .check-dichiarazioni {
                        margin-top: 10px;
                        display: block;
				    }         			

                    .pannello-dichiarazione
                    {
                        padding: 10px !important;
                    }
				</style>

				<script type="text/javascript">
					require(['jquery', 'jquery.ui'], function ($) {

					    

					    $(function () {
					        var pannelloDichiarazione = $(".pannello-dichiarazione");

					        if (pannelloDichiarazione.length) {

					            $('.invia').fadeToggle($('.check-dichiarazioni > input').is(':checked'));

					            $('.soggetti-firmatari').width(pannelloDichiarazione.width() + 20);

					            $('.check-dichiarazioni > input').click(function () {

					                $('.invia').fadeToggle($('.check-dichiarazioni > input').is(':checked'));
					            });
					        }

							$('.invia').click(onInvioClick);
						});

						function onInvioClick(e) {
							nascondiBottoni();
							mostraMessaggio();
						}

						function nascondiBottoni() {
							$('.bottoni').css('display', 'none')
						}

						function mostraMessaggio() {
							$('.messaggioInvio').dialog({
								width: 500,
								height: 100,
								title: 'Trasferimento dell\'istanza in corso',
								modal: true,
								closeOnEscape: false
							});
						}

                        
					});		
				</script>
                <%if (MostraGrigliaSottoscrittori) {%>
                <fieldset>
                    <legend>
                        <asp:Literal runat="server" ID="ltrIntestazioneSottoscrittori" Text="L'istanza deve essere firmata digitalmente da:" />
                    </legend>

                    <div>

					    <asp:GridView AutoGenerateColumns="false" 
									    Style="width: 70%" 
									    runat="server" 
									    ID="gvSoggettiFirmatari" 
                                        cssClass="soggetti-firmatari"
									    GridLines="None">
						    <Columns>
							    <asp:TemplateField HeaderText="Nominativo">
								    <ItemTemplate>
									    <asp:Literal runat="server" ID="ltrNome" Text='<%# DataBinder.Eval(Container,"DataItem") %>' />
								    </ItemTemplate>
							    </asp:TemplateField>
							    <asp:BoundField HeaderText="In qualità di" DataField="TipoSoggetto" />
						    </Columns>
					    </asp:GridView>

                    </div>                   
                </fieldset>
                <%} %>
                <fieldset>
                    <legend>
                        File caricato:
                    </legend>


					    <asp:HyperLink runat="server" ID="hlRiepilogoDomanda" Text="Visualizza il file caricato" /><br />
					
					    <asp:Label runat="server" 
								    ID="lblErroreRiepilogo" 
								    CssClass="errori" 
								    Text="Attenzione, il file non è stato firmato digitalmente"/>
				</fieldset>

                <fieldset>

                    <asp:Panel runat="server" id="pnlDichiarazione" CssClass="pannello-dichiarazione riepilogoDomandaHtml">
                        <asp:Label runat="server" ID="lblDichiarazione">Dichiarazione da accettare</asp:Label><br />
                        <asp:CheckBox runat="server" id="chkDichiarazione" Text="Check dichiarazione" TextAlign="right" CssClass="check-dichiarazioni checkbox" />
                    </asp:Panel>

				    <div class="bottoni">
					    <asp:Button runat="server" ID="cmdFirma" Text="Firma online" 
							    onclick="cmdFirma_Click" />

                        <asp:Button runat="server" ID="cmdFirmaCidPin" Text="Firma con CID/PIN" 
							    onclick="cmdFirmaCidPin_Click" />

                        <asp:Button runat="server" ID="cmdFirmaGrafometrica" Text="Firma grafometrica" 
							    onclick="cmdFirmaGrafometrica_Click" />

					    <asp:Button runat="server" ID="cmdFirnaConDispositivoEsterno" Text="Firma con dispositivo esterno" 
						    onclick="cmdFirnaConDispositivoEsterno_Click" />

                        <asp:Button runat="server" ID="cmdAllegaAltroFile" OnClick="cmdFirnaConDispositivoEsterno_Click" CssClass="allegato-ok" Text="Sostituisci il file allegato" />
					    <asp:Button runat="server" ID="cmdInviaDomanda" OnClick="cmdInviaDomanda_Click" CssClass="allegato-ok invia" Text="Trasferisci l'istanza al comune" />                        
				    </div>
                    
				    <div class="messaggioInvio">
					    <img src='<%= ResolveClientUrl("~/Images/ajax-loader.gif") %>' style="padding: 10px;" />
					    L'operazione potrebbe richiedere anche alcuni minuti. Si prega di attendere senza effettuare nessuna operazione
				    </div>

					
				    <asp:Panel runat="server" ID="pnlSoggettiNonSottoscriventi">
					    <legend>
						    <asp:Literal runat="server" ID="ltrSoggetiNonSottoscrittori" Text="I soggetti che non sottoscrivono sono" />
					    </legend>
					    <asp:GridView runat="server" Style="width: 70%" AutoGenerateColumns="false" ID="gvSoggettiNonSottoscriventi" GridLines="None">
						    <Columns>
							    <asp:TemplateField HeaderText="Nominativo">
								    <ItemTemplate>
									    <asp:Literal runat="server" ID="ltrNome" Text='<%# DataBinder.Eval(Container,"DataItem") %>' />
								    </ItemTemplate>
							    </asp:TemplateField>
							    <asp:BoundField HeaderText="In qualità di" DataField="TipoSoggetto" />
						    </Columns>
					    </asp:GridView>
				    </asp:Panel>

                </fieldset>
			</asp:View>

			<asp:View runat="server" ID="erroreInvioView">
				<asp:Label runat="server" ID="lblErroreInvio"></asp:Label>
			</asp:View>


		</asp:MultiView>
	</div>
</asp:Content>
