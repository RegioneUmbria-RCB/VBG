define(['jquery','jquery.ui'],function($){

function UploadAllegati(loaderUrl, urlBaseNote, idComune, token, software,idDomanda, provenienza) {
    this._editAttivo = -1;
    this._loaderUrl = loaderUrl;
    this._urlCaricamentoNote = urlBaseNote + '?IdComune=' + idComune +
                                            '&Token=' + token +
                                            '&Software=' + software +
                                            '&IdPresentazione=' + idDomanda +
                                            '&Provenienza=' + provenienza +
                                            '&IdAllegato=';
	
    this.mostraEdit  = _mostraEdit;
    this.nascondiEdit = _nascondiEdit;
    this.inizializza = _inizializza;

    this.inizializza();

    var image = new Image();
    image.src = this._loaderUrl;

    function _inizializza() {
		
        var self = this;

        // Preload dello spinner da visualizzare durante le chiamate ajax
        var preloadedImg = new Image();
        preloadedImg.src = this._loaderUrl;

        $('.editPanel').css('display', 'none');
        $('.sendPanel').each(function(idx){
            var el = $(this);

            $( '<img />', { src:self._loaderUrl } ).appendTo(el);
            $( '<b />' ).text('Caricamento del file in corso...').appendTo( el );
            $( '<br />' ).appendTo(el);
            $( '<span />' ).text('L\'invio di un file di grandi dimensioni potrebbe richiedere anche alcuni minuti').appendTo(el);

            el.css('display', 'none');      
        });
    
		
        $('.editLink').click(function (e) {
            e.preventDefault();

            var idFile = $(this).attr('idFile');

            self.mostraEdit(idFile);
        });

        $('.sendLink').click( function (e) {
            $('.editPanel').hide();
            $('.actionButton').hide();
            var idFile = $(this).attr('idFile');
            $('.sendPanel[idFile="' + idFile + '"]').css('display', 'block');

        });

        $('.cancelLink').click( function (e) {
            e.preventDefault();
            var idFile = $(this).attr('idFile');
            self.nascondiEdit(idFile);
        });

        $('#noteAllegato').dialog({
            title: 'Note di compilazione',
            modal: true,
            autoOpen: false
        });


        $('.allegato-contiene-note').click( function(e){
            var url = self._urlCaricamentoNote;
            url += $(this).data('idallegato');

            $('#noteAllegato').load( url , function(){
                $(this).dialog('open');
            });
        });
    }

    function _mostraEdit(idFile) {
        if (this._editAttivo != -1)
            this.nascondiEdit(this._editAttivo);

        $('.editPanel[idFile="' + idFile + '"]').css('display', 'block');
        $('.displayPanel[idFile="' + idFile + '"]').css('display', 'none');

        this._editAttivo = idFile;
    }

    function _nascondiEdit(idFile) {
        var idElementoEdit = '.editPanel[idFile="' + idFile + '"]';
        var idElementoFile = idElementoEdit + ' input[type=file]';

        $(idElementoFile).parent().html($(idElementoFile).parent().html());

        $(idElementoEdit).css('display', 'none');
        $('.displayPanel[idFile="' + idFile + '"]').css('display', 'block');

        this._editAttivo = -1;
    }

  }

  return UploadAllegati;
});