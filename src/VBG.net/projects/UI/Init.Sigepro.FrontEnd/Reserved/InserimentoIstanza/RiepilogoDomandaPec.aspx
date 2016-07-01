<%@ Page Title="Invio tramite PEC" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
	AutoEventWireup="true" CodeBehind="RiepilogoDomandaPec.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.RiepilogoDomandaPec" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="preInvioView">
			<asp:Label runat="server" ID="ltrIntestazioneInvio">
			In questa scheda è possibile seguire tre semplici passaggi per ricevere, nella propria casella di posta elettronica certificata (PEC), la domanda appena generata:
			<ol>
				<li>Scaricare il riepilogo dell’ istanza (domanda), firmarla digitalmente e ricaricarla firmata.</li>
				<li>Selezionare il destinatario a cui deve essere inviata la mail PEC formattata secondo quanto specificato dall’ “Art. 5 - Domande telematiche al SUAP” dell’allegato tecnico del dpr 7 settembre 2010 n.160 .</li>
				<li>Inviare l’istanza (domanda) alla casella PEC selezionata al punto 2.</li>
				<li>Una volta ricevuta la PEC nella propria casella di posta questa dovrà essere inoltrata al seguente indirizzo: <b>{INDIRIZZO_PEC_SPORTELLO}</b> facendo attenzione a non modificarla.</li>
			</ol>
			</asp:Label>
			<div class="inputForm">
				<fieldset>
					<ol>
						<li><div style="font-weight:bold;margin-bottom:5px">Riepilogo dell'istanza</div>
							<div>
								<div style="margin-bottom:5px">
									<asp:Label runat="server" ID="lblTestoDownloadDomanda">
										Scaricare il file pdf contenente il riepilogo dell'istanza
									</asp:Label>
								</div>
								<ul>
									<li>
										<asp:HyperLink runat="server" ID="hlModelloDomanda">
											<asp:Image ID="Image1" runat="server" ImageUrl="~/Images/pdf16x16.gif" />
											Riepilogo dell'istanza
										</asp:HyperLink>
									</li>
								</ul>
								<div style="margin-bottom:5px;line-height: 16px;">
									<asp:Label runat="server" ID="lblTestoUploadDomanda">
										Firmare il riepilogo dell’istanza<br />
										Ricaricare il riepilogo dell’istanza firmato
									</asp:Label>
								</div>
								<div>
									<%--<asp:Label runat="server" ID="Label4" Text="Selezionare il file da caricare" AssociatedControlID="fuRiepilogo"></asp:Label>--%>
									<asp:FileUpload runat="server" ID="fuRiepilogo" Style="width: 400px" />
								</div>
							</div>
						</li>
						<li><div  style="font-weight:bold;margin-bottom:5px;">Indicare il destinatario della mail</div>
							<div>
								<div>
									<div>
										<asp:Label runat="server" ID="lblSelezioneIndirizzoPEC">
										</asp:Label>
									</div>
								</div>
								<div>
									<asp:DropDownList runat="server" ID="ddlIndirizzoPec">
									</asp:DropDownList>
								</div>
								<br />
								<div>
									<div>
										<asp:Label runat="server" ID="lblIndirizzoSportelloComune">
							L'indirizzo email dello sportello a cui inoltrare il pacchetto ricevuto è <b>{INDIRIZZO_PEC_SPORTELLO}</b><br />
										</asp:Label>
									</div>
								</div>
							</div>
						</li>
						<li><div  style="font-weight:bold;margin-bottom:5px;">Invia la mail all'indirizzo di PEC specificato </div>
							<div>
								<div>
									<div>
										<asp:Label runat="server" ID="lblTestoBottoneInvio">
										</asp:Label>
									</div>
								</div>
								<div class="bottoni">
									<asp:Button runat="server" ID="cmdUploadDomanda" Text="Genera il pacchetto ed effettua l'invio all'indirizzo PEC specificato"
										OnClick="cmdUploadDomanda_Click" />
								</div>
							</div>
						</li>
					</ol>
				</fieldset>
			</div>
		</asp:View>
		<asp:View runat="server" ID="invioEffettuatoView">
			<div class="inputForm">
				<fieldset>
					<div style="margin-bottom:5px">
						<asp:Literal runat="server" ID="ltrInvioeffettuato">
						La mail è stata inviata con successo all'indirizzo email specificato
						</asp:Literal> 
						<br />
						<br />
						L'indirizzo dello sportello del comune a cui inoltrare la domanda è: <b><asp:Literal runat="server" ID="ltrEmailDestinatario" /></b>
					</div>
				</fieldset>
			</div>
		</asp:View>
		<asp:View runat="server" ID="erroriView">
			<fieldset>
				<legend>Si è verificato un errore durante l'invio del pacchetto della domanda</legend>
				<div>
					<asp:Literal runat="server" ID="lblErroreInvio" />
				</div>
			</fieldset>
		</asp:View>
	</asp:MultiView>
</asp:Content>
