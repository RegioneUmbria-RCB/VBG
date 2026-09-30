using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella MERCATIPRESENZE_T per la classe MercatiPresenzeT il 29/10/2008 10.40.25
    ///
    ///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
    ///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
    /// -
    /// -
    /// -
    /// - 
    ///
    ///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
    ///
    public partial class MercatiPresenzeTMgr : BaseManager
    {
        public MercatiPresenzeTMgr(DataBase dataBase) : base(dataBase) { }

        public List<MercatiPresenzeT> GetList(MercatiPresenzeT filtro)
        {
            return this.db.GetClassList(filtro).ToList<MercatiPresenzeT>();
        }

        public MercatiPresenzeT Insert(MercatiPresenzeT cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private MercatiPresenzeT ChildInsert(MercatiPresenzeT cls)
        {
            return cls;
        }

        private MercatiPresenzeT DataIntegrations(MercatiPresenzeT cls)
        {
            return cls;
        }


        public MercatiPresenzeT Update(MercatiPresenzeT cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(MercatiPresenzeT cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(MercatiPresenzeT cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void Validate(MercatiPresenzeT cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


