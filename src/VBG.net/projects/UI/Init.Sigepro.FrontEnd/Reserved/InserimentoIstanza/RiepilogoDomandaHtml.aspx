<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" Codebehind="RiepilogoDomandaHtml.aspx.cs"
	Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.RiepilogoDomandaHtml" Title="Untitled Page" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="cc1" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="riepilogoView">

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
				</style>

				<script type="text/javascript">

					require( ['jquery','jquery.ui'],function($){
						$(function() {
					
							var iFrame = $('#iFrameDomanda');
						
							$('#divCaricamentoFrame').css({
								'width': iFrame.width() + 'px',
								'height': iFrame.height() + 'px',
								'position': 'absolute',
								'top': iFrame.position().top,
								'left': iFrame.position().left
							});

							<%if(!RiepilogoRichiedeFirma()) {%>
								$('#<%=cmdProcedi.ClientID %>').click(onInvioClick);					
							
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
						    <%} %>

						    $('#<%=cmdScaricaVersioneStampabile.ClientID%>').on('click', function (e) {
						        var url = '<%=GetUrlVersioneStampabile()%>';

						        e.preventDefault();

						        window.open(url);
						    });

						});

						function frameLoadingCompleted() {
							$('#divCaricamentoFrame').css('display', 'none');
						}

						window.frameLoadingCompleted = frameLoadingCompleted;
					});

					
				</script>
				
			<div class="inputForm" id="mainForm">
			
				<div>
					<asp:Literal runat="server" ID="ltrDescrizioneFaseRiepilogo" />
				</div>
			
				<fieldset>
                    <%if (MostraRiepilogoDomanda){%>
					    <div id='divCaricamentoFrame' style='text-align:center'>
						    <img src="../../Images/ajax-loader.gif" /><span style='font-size:15px'>Generazione del riepilogo in corso...</span>
					    </div>

                    
					    <iframe id="iFrameDomanda" class="riepilogoDomandaHtml" onload="frameLoadingCompleted();" src='<%= GetUrlRiepilogoDomanda() %>'></iframe>

                    <%} %>
					
					<div class="bottoni">
                        <asp:Button runat="server" ID="cmdScaricaVersioneStampabile" Text="Scarica versione stampabile" Visible='<%#MostraRiepilogoDomanda%>' />
						<asp:Button runat="server" ID="cmdProcedi" Text="Procedi" OnClick="cmdProcedi_Click" />
					</div>
				</fieldset>
			</div>

			<div class="messaggioInvio">
				<img src='<%= ResolveClientUrl("~/Images/ajax-loader.gif") %>' style="padding: 10px;" />
				L'operazione potrebbe richiedere anche alcuni minuti. Si prega di attendere senza effettuare nessuna operazione
			</div>
		</asp:View>
	
		<asp:View runat="server" ID="erroreInvioView">
			<asp:Label runat="server" ID="lblErroreInvio"></asp:Label>
		</asp:View>
	</asp:MultiView>
</asp:Content>
