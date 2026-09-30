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
		
        var self = this,
            noteAllegatoJq = $('#noteAllegato'),
            noteAllegatoBs = $('.note-allegato');

        // Preload dello spinner da visualizzare durante le chiamate ajax
        var preloadedImg = new Image();
        preloadedImg.src = this._loaderUrl;

        $('.editPanel').css('display', 'none');
		
        $('.editLink').click(function (e) {
            e.preventDefault();

            var idFile = $(this).attr('idFile');

            self.mostraEdit(idFile);
        });

        $('.sendLink').click( function (e) {
            $('.editPanel').hide();
            $('.actionButton').hide();
            var idFile = $(this).attr('idFile');
            
            $('.modal-caricamento-file-in-corso').modal('show')
        });

        $('.cancelLink').click( function (e) {
            e.preventDefault();
            var idFile = $(this).attr('idFile');
            self.nascondiEdit(idFile);
        });

        if (noteAllegatoBs.length === 0) {
            noteAllegatoJq.dialog({
                title: 'Note di compilazione',
                modal: true,
                autoOpen: false
            });
        }




        $('.allegato-contiene-note').click( function(e){
            var url = self._urlCaricamentoNote;
            url += $(this).data('idallegato');

            if (noteAllegatoBs.length === 0) {
                noteAllegatoJq.load(url, function () {
                    $(this).dialog('open');
                });
            } else {
                $.ajax({
                    url: url,
                    success: function (data) {
                        console.log(data);
                        noteAllegatoBs.find(".modal-body").html(data);
                        noteAllegatoBs.modal("show");
                    },
                    dataType: 'html'
                });

            }
        });
    }

    function _mostraEdit(idFile) {
        if (this._editAttivo !== -1)
            this.nascondiEdit(this._editAttivo);

        $('.editPanel[idFile="' + idFile + '"]').css('display', 'block');
        $('.displayPanel[idFile="' + idFile + '"]').css('display', 'none');
        $('.editPanel[idFile="' + idFile + '"]>input[type=file]').click();
        $('.editPanel[idFile="' + idFile + '"]>input[type=file]').on('change', function (e) {
            console.log(e.target.value);
            var el = $(e.target).closest('tr').find('a.sendLink');

            if (el.length > 0) {
                el[0].click();
            }
        });

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