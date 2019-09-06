using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using SIGePro.Net.WebServices.WSSIGeProSmtpMail;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using Init.SIGePro.Manager;
using SIGePro.Tasks;
using Init.SIGePro.Manager.Logic.SmtpMail;

namespace Sigepro.net.Utilita.Mail
{
    public class SchedulerSorteggiMessage
    {
        protected string m_idcomune = String.Empty;
        protected int? m_idschedulazione = null;
        protected int? m_idsorteggio = null;
        protected DataBase m_database = null;

        public string IdComune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

		public string Software { get; protected set; }

        public int? IdSorteggio
        {
            get { return m_idsorteggio; }
            set { m_idsorteggio = value; }
        }

        public int? IdSchedulazione
        {
            get { return m_idschedulazione; }
            set { m_idschedulazione = value; }
        }

        public DataBase Database
        {
            get { return m_database; }
            set { m_database = value; }
        }

        public SchedulerSorteggiMessage( )
        { 
        
        }

        public SchedulerSorteggiMessage(DataBase database, string idcomune, int idsorteggio, int idschedulazione)
        {
            Database = database;
            IdComune = idcomune;
            IdSorteggio = idsorteggio;
            IdSchedulazione = idschedulazione;
        }

        public SIGeProMailMessage GetMessage()
        { 
            SIGeProMailMessage smm = new SIGeProMailMessage();

            SorteggiTestata st = new SorteggiTestata();
            st.Idcomune = IdComune;
            st.StId = IdSorteggio;
            st.UseForeign = useForeignEnum.Yes;
            st = new SorteggiTestataMgr( Database ).GetByClass( st );

			Software = st.Software;

            TaskSorteggi ts = new TaskSorteggi(Database, IdComune, IdSchedulazione.GetValueOrDefault(int.MinValue));

            #region SorteggiTestata.Attachments
            if (st.Oggetto != null)
            {
                if (st.Oggetto.OGGETTO == null)
					st.Oggetto = new OggettiMgr(Database).GetById(st.Oggetto.IDCOMUNE, Convert.ToInt32(st.Oggetto.CODICEOGGETTO));

                BinaryObject bo = new BinaryObject();
                bo.FileContent = st.Oggetto.OGGETTO;
                bo.FileName = st.Oggetto.NOMEFILE;

                smm.Attachments = new BinaryObject[1];
                smm.Attachments[0] = bo;
            }
            #endregion

            #region SorteggiTestata.CorpoMail e SorteggiTestata.Oggetto
            MailTipoMgr mgr = new MailTipoMgr(Database);
            
            MailTipo mt = mgr.GetById(ts.CodiceMailTipo.ToString(), IdComune);
            mgr.IdSorteggio = IdSorteggio.GetValueOrDefault(int.MinValue);

            smm.CorpoMail = mgr.GetBody(mt);

            smm.Oggetto = mgr.GetSubject( mt );
            #endregion

            #region SorteggiTestata.Destinatari
            smm.Destinatari = ts.MailDestinatario;
            #endregion

            #region SorteggiTestata.InviaComeHtml
            smm.InviaComeHtml = true;
            #endregion

            return smm;
        }
    }
}
