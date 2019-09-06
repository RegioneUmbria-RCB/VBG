<%@ Page Title="Titolo" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneLocalizzazioniSitModena.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneLocalizzazioniSitModena" %>

<%@ Register Assembly="Init.Utils" Namespace="Init.Utils.Web.UI" TagPrefix="cc2" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content2" ContentPlaceHolderID="head" runat="server">
    <style>
        .icona-mappa {
            width: 20px;
            height: 20px;
            vertical-align: bottom;
            margin-bottom: 2px;
            cursor: pointer;
        }
    </style>



</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

    <script type="text/javascript">
        $('.icona-mappa').each(function (idx, el) {
            if ($(el).data('urlVisualizzazione') === '' || $(el).data('urlVisualizzazione') === undefined) {
                $(el).hide();
            }

            el.getUrl = function () {
                var self = $(this),
                    sostituzioni = [
                        { ph: '$CIVICO$', val: 'civico' },
                        { ph: '$ESPONENTE$', val: 'esponente' },
                        { ph: '$CODVIARIO$', val: 'codViario' },
                        { ph: '$TIPOCATASTO$', val: 'tipoCatasto' },
                        { ph: '$FOGLIO$', val: 'foglio' },
                        { ph: '$PARTICELLA$', val: 'particella' },
                        { ph: '$SUB$', val: 'sub' },
                        { ph: '$CODCIVICO$', val: 'codCivico' },
                        { ph: '$SEZIONE$', val: 'sezione' },
                        { ph: '$FABBRICATO$', val: 'fabbricato' },
                    ],
                    url = self.data('urlVisualizzazione');

                for (var i = 0; i < sostituzioni.length; i++) {
                    var val = self.data(sostituzioni[i].val) || '';

                    url = url.replace(sostituzioni[i].ph, val);
                }

                return url;
            }
        });

        $('.icona-mappa').on('click', function (e) {
            var el = $(this),
                url = this.getUrl(),
                width = 700,
                height = 500,
                top = (screen.height - height) / 2,
                left = (screen.width - width) / 2,
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
    </script>

    <div class="inputForm">

        <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
            <asp:View runat="server" ID="listaView">
                <fieldset>
                    <asp:GridView ID="dgStradario" Width="100%"
                        AutoGenerateColumns="False"
                        DataKeyNames="Id"
                        OnSelectedIndexChanged="dgStradario_SelectedIndexChanged"
                        OnRowDeleting="dgStradario_DeleteCommand"
                        GridLines="None"
                        CssClass="table"
                        runat="server">
                        <Columns>
                            <asp:TemplateField HeaderText="Indirizzo">
                                <ItemTemplate>
                                    <img src='<%=ResolveClientUrl("~/images/map-icon-small.png") %>' class="icona-mappa"
                                        data-cod-viario='<%# DataBinder.Eval(Container.DataItem,"CodiceViario")%>'
                                        data-cod-civico='<%# DataBinder.Eval(Container.DataItem,"CodiceCivico")%>'
                                        data-civico='<%# DataBinder.Eval(Container.DataItem,"Civico")%>'
                                        data-esponente='<%# DataBinder.Eval(Container.DataItem,"Esponente")%>'
                                        data-url-visualizzazione='<%=UrlLocalizzazioneDaIndirizzo %>' />
                                    <asp:Literal runat="server" ID="ltrindirizzo" Text='<%# DataBinder.Eval(Container,"DataItem")%>' />
                                </ItemTemplate>
                            </asp:TemplateField>

                            <asp:TemplateField HeaderText="Altri dati">
                                <ItemTemplate>
                                    <asp:Literal runat="server" ID="ltrAltriDati" Text='<%# FormattaAltriDati(DataBinder.Eval(Container,"DataItem"))%>' />
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:BoundField DataField="Km" />
                            <asp:BoundField DataField="Note" HeaderText="Note" />
                            <asp:TemplateField HeaderText="Coordinate">
                                <ItemTemplate>
                                    <asp:Literal runat="server" ID="txtLongitudine" Text='<%# FormattaCoordinate(DataBinder.Eval(Container,"DataItem"))%>' />
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
                                        data-url-visualizzazione='<%=UrlLocalizzazioneDaMappali %>' />
                                    <asp:Literal runat="server" ID="txtRifCat" Text='<%# Bind("PrimoRiferimentoCatastale") %>' />
                                </ItemTemplate>
                            </asp:TemplateField>

                            <asp:TemplateField HeaderStyle-Width="150px" ItemStyle-HorizontalAlign="Right">
                                <ItemTemplate>
                                    <asp:LinkButton ID="lnkModifica" runat="server" Text="Modifica" CommandName="Select" />
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

                <%=LoadScript("~/js/app/autocompleteStradario.js") %>

                <script type="text/javascript">

                    function DialogValidazione() {
                        return {
                            mostra: function () {
                                $("#dialog-validazione").dialog({
                                    modal: true,
                                    title: 'Validazione'
                                });
                            },
                            nascondi: function () {
                                $("#dialog-validazione").dialog('close');
                            }
                        }
                    }


                    var dialogValidazione = new DialogValidazione(),
                        baseUrl = '<%= ResolveClientUrl("~/Reserved/InserimentoIstanza/IntegrazioneSit/IntegrazioneSitService.asmx")%>',
                            urlListaCampi = baseUrl + '/GetListaCampi?idcomune=<%=IdComune%>&software=<%=Software %>',
                            urlValidazione = baseUrl + '/ValidaCampo?idcomune=<%=IdComune%>&software=<%=Software %>',
                            campi = {
                                indirizzo: $("#<%= acIndirizzo.ClientID %>"),
                                civico: $("#<%=txtCivico.Inner.ClientID%>"),
                                esponente: $("#<%=txtEsponente.Inner.ClientID%>"),
                                codViario: $("#<%=hiddenCodViario.ClientID %>"),
                                tipoCatasto: $("#<%=ddlTipoCatasto.Inner.ClientID%>"),
                                foglio: $("#<%=txtFoglio.Inner.ClientID%>"),
                                particella: $("#<%=txtParticella.Inner.ClientID%>"),
                                sub: $("#<%=txtSub.Inner.ClientID%>"),
                                codCivico: $('#<%=hiddenCodCivico.ClientID %>'),
                                sezione: $('#<%=hiddenSezione.ClientID %>'),
                                fabbricato: $("#<%=txtFabbricato.Inner.ClientID%>")
                        },
                        campiIdx = [];

                    $(".ricerca-sit").each(function () {
                        var $this = $(this),
                            group = $("<div class='input-group'></div>"),
                            addon = $("<span class='input-group-addon'><i class='glyphicon glyphicon-search'></i></span>"),
                            textCtrl = $this.find(">input[type=text]");

                        if (!textCtrl.length) {
                            return;
                        }

                        addon.appendTo(group);
                        group.appendTo($this);

                        textCtrl.appendTo(group);
                        $this.find(">.help-block").appendTo($this);

                    });



                    campi.codViario.parent().find('input[type=\'text\']').on('keydown', function () {
                        campi.civico.val('');
                        campi.esponente.val('');
                        campi.tipoCatasto.val('');
                        campi.foglio.val('');
                        campi.particella.val('');
                        campi.sub.val('');
                        campi.codCivico.val('');
                        campi.sezione.val('');
                        campi.fabbricato.val('');
                    });

                    campi.civico.on('keydown', function () {
                        campi.esponente.val('').data('validated', 0);
                        campi.tipoCatasto.val('').data('validated', 0);
                        campi.foglio.val('').data('validated', 0);
                        campi.particella.val('').data('validated', 0);
                        campi.sub.val('').data('validated', 0);
                        campi.codCivico.val('').data('validated', 0);
                        campi.sezione.val('').data('validated', 0);
                        campi.fabbricato.val('').data('validated', 0);
                    });

                    campi.esponente.on('keydown', function () {
                        campi.tipoCatasto.val('').data('validated', 0);
                        campi.foglio.val('').data('validated', 0);
                        campi.particella.val('').data('validated', 0);
                        campi.sub.val('').data('validated', 0);
                        campi.codCivico.val('').data('validated', 0);
                        campi.sezione.val('').data('validated', 0);
                        campi.fabbricato.val('').data('validated', 0);
                    });

                    function setTipoCatasto(tipoCatasto) {
                        campi.tipoCatasto.val(tipoCatasto);
                        campi.tipoCatasto.trigger('change');
                    }

                    function validate(el) {

                        var campoErrore = el.parent().find('.input-validation'),
                            onSuccess,
                            onBeforeSend,
                            onComplete,
                            ajaxRequestParams;

                        console.log('Inizio validazione');

                        if (el.val() === '')
                            return;

                        onSuccess = function (d) {
                            var data = d.d,
                                validatedFields = $('.input-validation');

                            validatedFields.html('');
                            validatedFields.removeClass('error');

                            if (data.error) {
                                campoErrore.addClass('error');
                                campoErrore.html(data.errorDescription);
                                el.val('');
                                console.log('validazione fallita');

                                return;
                            }

                            campi.civico.val(data.civico);
                            campi.esponente.val(data.esponente);
                            campi.foglio.val(data.foglio);
                            campi.particella.val(data.particella);
                            campi.sub.val(data.sub);
                            campi.codCivico.val(data.codCivico);
                            campi.sezione.val(data.sezione);
                            campi.fabbricato.val(data.fabbricato);

                            setTipoCatasto(data.tipoCatasto);

                            console.log('validazione completata con successo');
                            el.data('validated', 1);
                        };

                        onBeforeSend = function () {
                            dialogValidazione.mostra();
                        };
                        onComplete = function () {
                            dialogValidazione.nascondi();
                        };


                        ajaxRequestParams = {
                            url: urlValidazione,
                            beforeSend: onBeforeSend,
                            complete: onComplete,
                            type: "POST",
                            contentType: "application/json; charset=utf-8",
                            dataType: "json",
                            data: valoriCampi(el)
                        };

                        $.ajax(ajaxRequestParams).done(onSuccess);
                    }




                    function valoriCampi(el) {
                        return JSON.stringify({
                            nomeCampo: el.data('campoSit'),
                            codiceStradario: campi.codViario.val(),
                            civico: campi.civico.val(),
                            // codCivico:			campi.codCivico.val(),
                            esponente: campi.esponente.val(),
                            tipoCatasto: campi.tipoCatasto.val() || 'F',
                            sezione: '',
                            foglio: campi.foglio.val(),
                            particella: campi.particella.val(),
                            sub: campi.sub.val() || '',
                            fabbricato: campi.fabbricato.val(),
                            accessoTipo: "",
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
                                                    label: item,
                                                    id: item,
                                                    value: item
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
                                    minLength: 0,
                                    search: function () {
                                        el.removeClass('valid');
                                    },
                                    response: function () {
                                        el.closest(".input-group")
                                            .find(".input-group-addon>i")
                                            .removeClass('glyphicon-refresh spin')
                                            .addClass("glyphicon-search");
                                    }
                                });

                                el.on("autocompleteclose change", function (event, ui) {
                                    console.log('Validazione valore selezionato');
                                    validate(el);
                                });
                            };



                        $('.ricerca-sit input').each(function (i, el) {
                            el = $(el);

                            el.on('change', function () {
                                el.data('validated', 0);
                            });

                            el.on('keydown', function () {
                                el.data('validated', 0);
                            });

                            bindAutocomplete(el);

                            var startAutocomplete = function () {
                                var valToSearch = el.val() === '' ? ' ' : el.val();

                                if (el.data('validated') === 1) {
                                    return;
                                }

                                el.closest(".input-group")
                                    .find(".input-group-addon>i")
                                    .removeClass("glyphicon-search")
                                    .addClass("glyphicon-refresh spin");

                                el.autocomplete('search', valToSearch);
                            };

                            el.on('focus', startAutocomplete);
                            el.on('click', startAutocomplete);
                            el.on('keydown', startAutocomplete);
                            el.on('focusout', function () {
                                var i = el.closest(".input-group")
                                    .find(".input-group-addon>i");

                                if (i.hasClass("spin")) {
                                    i.removeClass("glyphicon-refresh spin")
                                        .addClass("glyphicon-search");
                                }
                            });
                        });
                    }

                    function processaUrl(url) {
                        url = url.replace("$CIVICO$", campi.civico.val());
                        url = url.replace("$ESPONENTE$", campi.esponente.val());
                        url = url.replace("$CODVIARIO$", campi.codViario.val());
                        url = url.replace("$TIPOCATASTO$", campi.tipoCatasto.val());
                        url = url.replace("$FOGLIO$", campi.foglio.val());
                        url = url.replace("$PARTICELLA$", campi.particella.val());
                        url = url.replace("$SUB$", campi.sub.val());
                        url = url.replace("$CODCIVICO$", campi.codCivico.val());
                        url = url.replace("$SEZIONE$", campi.sezione.val());

                        return url;
                    }

                    function VisualizzazioneMappa(el, url) {
                        var a = $('<a />', { 'href': '#' }),
                            imgAttributes = {
                                'src': '<%=ResolveClientUrl("~/images/map-icon-small.png") %>',
                                'style': 'width:20px; height:20px; vertical-align: bottom; margin-bottom: 2px;'
                            },
                            img = $("<img />", imgAttributes);

                        img.appendTo(a);
                        a.appendTo(el.parent());

                        a.on('click', function (e) {
                            var width = 700,
                                height = 500,
                                top = (screen.height - height) / 2,
                                left = (screen.width - width) / 2,
                                feats = "height=" + height + ", width=" + width + ", menubar=no, resizable=yes,scrollbars=yes,status=no,toolbar=no,top=" + top + ", left=" + left,
                                w = window.open(processaUrl(url), 'sit_view', feats);

                            console.log('Caratteristiche finestra: ', feats);

                            e.preventDefault();
                            w.focus();

                        });
                    }



                    $(function () {

                        var tipoCatasto = $('.tipoCatasto>select')
                        var subalterno = $('.subalterno');

                        tipoCatasto.on('change', function () {
                            var display = $(this).val() == 'F' ? 'block' : 'none';

                            subalterno.val('');
                            subalterno.css('display', display);
                        });

                        tipoCatasto.trigger('change');

                        RicercaSit($);

							    <% if (MostraLocalizzazioneDaIndirizzo)
                    {%>
                            VisualizzazioneMappa($("[data-campo-sit='Esponente'] > div > input"), '<%=UrlLocalizzazioneDaIndirizzo%>');
							    <% } %>

							    <% if (MostraLocalizzazioneDaMappali)
                    {%>
                            VisualizzazioneMappa($("[data-campo-sit='Particella'] > div > input"), '<%=UrlLocalizzazioneDaMappali%>');
							    <% } %>
                    });

                    new AutocompleteStradario({
                        idCampoIndirizzo: '<%= acIndirizzo.Inner.ClientID %>',
                            idCampoCodiceStradario: '<%= acIndirizzo.HiddenClientID%>',
                            idCampoCodViario: '<%= hiddenCodViario.ClientID%>',
                            serviceUrl: '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteStradario.asmx") %>/AutocomlpeteStradario',
                            idComune: '<%=IdComune %>',
                            codiceComune: '<%=CodiceComune %>'
                    });

                </script>

                <asp:GridView runat="server" ID="dgIndirizzi"
                    GridLines="None"
                    AutoGenerateColumns="False"
                    DataKeyNames="CODICESTRADARIO"
                    OnSelectedIndexChanged="dgIndirizzi_SelectedIndexChanged">
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

                <div class="form-small">

                    <div>
                        <asp:HiddenField runat="server" ID="hiddenCodViario" />
                        <asp:HiddenField runat="server" ID="hiddenCodCivico" />
                        <asp:HiddenField runat="server" ID="hiddenSezione" />
                        <asp:HiddenField runat="server" ID="hiddenIdLocalizzazione" />
                        <ar:Autocomplete runat="server" ID="acIndirizzo" MaxLength="100" Label="Indirizzo" Required="true" />
                        <asp:HiddenField runat="server" ID="txtAccessoTipo" />
                        <asp:HiddenField runat="server" ID="txtAccessoNumero" />
                        <asp:HiddenField runat="server" ID="txtAccessoDescrizione" />


                        <span id="errMsgOuput"></span>
                    </div>

                    <div class="row">
                        <ar:TextBox runat="server" ID="txtCivico" Label="Civico" MaxLength="10" CssClass="ricerca-sit" InnerAttributes="data-campo-sit=Civico" BtSize="Col4" />
                        <ar:TextBox runat="server" ID="txtEsponente" Label="Esponente" MaxLength="10" CssClass="ricerca-sit" InnerAttributes="data-campo-sit=Esponente" BtSize="Col4" />
                        <ar:DropDownList runat="server" ID="ddlColore" Label="Colore" DataTextField="COLORE" DataValueField="CODICECOLORE" CssClass="ricerca-sit" InnerAttributes="data-campo-sit=Colore" BtSize="Col4" />
                    </div>
                    <div class="row">
                        <ar:TextBox runat="server" ID="txtScala" Label="Scala" MaxLength="10" CssClass="ricerca-sit" InnerAttributes="data-campo-sit=Scala" data-campo-sit="Scala" BtSize="Col4" />
                        <ar:TextBox runat="server" ID="txtPiano" Label="Piano" MaxLength="10" CssClass="ricerca-sit" InnerAttributes="data-campo-sit=Piano" data-campo-sit="Piano" BtSize="Col4" />
                        <ar:TextBox runat="server" ID="txtInterno" Label="Interno" MaxLength="10" CssClass="ricerca-sit" InnerAttributes="data-campo-sit=Interno" data-campo-sit="Interno" BtSize="Col4" />
                    </div>

                    <div class="row">
                        <ar:TextBox runat="server" ID="txtEsponenteInterno" Label="Esponente interno" MaxLength="10" CssClass="ricerca-sit" InnerAttributes="data-campo-sit=EsponenteInterno" data-campo-sit="EsponenteInterno" BtSize="Col4" />
                        <ar:TextBox runat="server" ID="txtFabbricato" Label="Fabbricato" MaxLength="10" CssClass="ricerca-sit" InnerAttributes="data-campo-sit=Fabbricato" data-campo-sit="Fabbricato" BtSize="Col4" />
                        <ar:TextBox runat="server" ID="txtKm" Label="Km" MaxLength="10" CssClass="ricerca-sit" InnerAttributes="data-campo-sit=Km" data-campo-sit="Km" BtSize="Col4" />
                    </div>

                    <ar:TextBox runat="server" ID="txtNote" Label="Note" MaxLength="80" Rows="4" TextMode="MultiLine" />


                    <ar:TextBox runat="server" ID="txtLongitudine" Label="Longitudine" MaxLength="50" CssClass="ricerca-sit" InnerAttributes="data-campo-sit=Longitudine" data-campo-sit="Longitudine" />
                    <ar:TextBox runat="server" ID="txtLatitudine" Label="Latitudine" MaxLength="50" CssClass="ricerca-sit" InnerAttributes="data-campo-sit=Latitudine" data-campo-sit="Latitudine" />

                </div>




                <div class="form-small">
                    <h3>Riferimenti catastali</h3>

                    <div class="row">
                        <ar:DropDownList runat="server" ID="ddlTipoCatasto" Label="Tipo catasto" CssClass="tipoCatasto ricerca-sit" InnerAttributes="data-campo-sit=TipoCatasto" data-campo-sit="TipoCatasto" BtSize="Col3" />
                        <ar:TextBox runat="server" ID="txtFoglio" Label="Foglio" MaxLength="10" Columns="7" CssClass="ricerca-sit" InnerAttributes="data-campo-sit=Foglio" data-campo-sit="Foglio" BtSize="Col3" />
                        <ar:TextBox runat="server" ID="txtParticella" Label="Particella" MaxLength="10" Columns="7" CssClass="ricerca-sit" InnerAttributes="data-campo-sit=Particella" data-campo-sit="Particella" BtSize="Col3" />
                        <ar:TextBox runat="server" ID="txtSub" Label="Subalterno" MaxLength="10" Columns="7" CssClass="subalterno ricerca-sit" InnerAttributes="data-campo-sit=Sub" data-campo-sit="Sub" BtSize="Col3" />
                    </div>
                </div>

                <div id="dialog-validazione" style="display: none">
                    Validazione dei dati in corso
                </div>

                <div class="form-small">
                    <asp:Button ID="cmdConfirm" runat="server" CssClass="btn btn-primary" Text="Conferma" OnClick="cmdConfirm_Click" />
                    <asp:LinkButton ID="cmdCancel" runat="server" CssClass="btn btn-default" Text="Annulla" OnClick="cmdCancel_Click" />
                </div>
            </asp:View>
        </asp:MultiView>

    </div>
</asp:Content>

