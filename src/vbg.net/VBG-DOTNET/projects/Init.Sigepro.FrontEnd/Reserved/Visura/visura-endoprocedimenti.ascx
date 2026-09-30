<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="visura-endoprocedimenti.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Visura.visura_endoprocedimenti" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<style>
    .nome-allegato-endo {
        padding: 3px 20px;
        font-weight: bold;
    }

    .download-allegato-endo {
        margin-bottom: var(--padding-small);
        /*padding-left: var(--padding-default);*/
    }

    .download-allegato-endo> a {
        padding: 0;
    }
</style>

<asp:GridView GridLines="None" runat="server" ID="dgProcedimenti" CssClass="table" AutoGenerateColumns="false" OnRowDataBound="dgProcedimenti_RowDataBound">
    <Columns>
        <asp:TemplateField HeaderText="Procedimento">
            <ItemTemplate>
                <asp:Literal runat="server" ID="ltrProcedimento" Text='<%# DataBinder.Eval(Container.DataItem , "Endoprocedimento")%>' />
            </ItemTemplate>
        </asp:TemplateField>
        <asp:TemplateField HeaderText="Allegati">
            <ItemTemplate>

                <asp:Repeater runat="server" ID="rptAllegatiEndo">
                    <HeaderTemplate>

                        <div class="dropdown">
                            <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                <asp:Literal runat="server" ID="ltrNumeroAllegati" />
                                <span class="caret"></span>
                            </button>

                            <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                    </HeaderTemplate>

                    <ItemTemplate>

                        <li class="nome-allegato-endo">
                            <%# Eval("Descrizione") %>
                        </li>
                        <li class="download-allegato-endo">
                            <a href="<%# Eval("Url") %>" target="_blank">
                                <%# Eval("NomeFile") %>
                                <%# Eval("Md5", ", MD5: {0}")%>
                            </a>
                        </li>

                    </ItemTemplate>

                    <FooterTemplate>
                        </ul>
                            </div>
                    </FooterTemplate>
                </asp:Repeater>

            </ItemTemplate>
        </asp:TemplateField>
    </Columns>
    <EmptyDataTemplate>
        <div class="alert alert-info">Nella pratica non sono presenti endoprocedimenti attivati</div>
    </EmptyDataTemplate>
</asp:GridView>

<ar:BootstrapModal runat="server" ID="bmAllegatiEndo" Title="Allegati dell'endoprocedimento" ShowOkButton="false">
    <ModalBody>
        <div class="lista-allegati-endo"></div>
    </ModalBody>
</ar:BootstrapModal>
