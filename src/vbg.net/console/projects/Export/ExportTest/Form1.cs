using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;
using PersonalLib2.Data;
using System.IO;
using ICSharpCode.SharpZipLib.Zip;
using System.Threading;
using System.Web.Services.Protocols;
using ExportTest.WSSigeproLogin;
using System.Configuration;

namespace ExportTest
{
	/// <summary>
	/// Descrizione di riepilogo per Form1.
	/// </summary>
	public class Form1 : System.Windows.Forms.Form
	{
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.TextBox txXmlText;
		private System.Windows.Forms.Button btnList;
		private System.Windows.Forms.OpenFileDialog openFileDialog1;
		private System.Windows.Forms.Button btnOpen;
		private System.Windows.Forms.TextBox txtFile;
		private System.Windows.Forms.ComboBox cmbCompLvl;
        private System.Windows.Forms.Button btnCmp;
		private ZipOutputStream archive;
		private System.Windows.Forms.Button btnExpService;
		private System.Windows.Forms.TextBox txtToken;
		private System.Windows.Forms.Label label3;
		private System.Windows.Forms.GroupBox groupBox1;
		private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label6;
		private System.Windows.Forms.GroupBox groupBox2;
		private string[] filenames;
		private System.Windows.Forms.Button btnDetail;
		private System.Windows.Forms.TextBox txtID;
		private System.Windows.Forms.Label label2;
		private System.Windows.Forms.TextBox txtIdComune;
		private System.Windows.Forms.Label label5;
		/// <summary>
		/// Variabile di progettazione necessaria.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public Form1()
		{
			InitializeComponent();

			ArrayList pCmbVal = new ArrayList();
			pCmbVal.Add(new CValCmb("None", 0));
			pCmbVal.Add(new CValCmb("Lowest", 1)); 
			pCmbVal.Add(new CValCmb("Low", 3));
			pCmbVal.Add(new CValCmb("Best", 9));
			pCmbVal.Add(new CValCmb("Normal", -1));
			
			cmbCompLvl.DataSource = pCmbVal;
			cmbCompLvl.DisplayMember = "Text";
			cmbCompLvl.ValueMember = "Value";
		}

        private DataBase CreateDatabase(AuthenticationInfo authInfo)
        {
            ProviderType initialProviderType = (ProviderType)Enum.Parse(typeof(ProviderType), authInfo.Provider, true);
            string sConnection;
            if (authInfo.ConnectionString.EndsWith(";"))
                sConnection = authInfo.ConnectionString + "User Id=" + authInfo.DBUser + ";Password=" + authInfo.DBPassword;
            else
                sConnection = authInfo.ConnectionString + ";User Id=" + authInfo.DBUser + ";Password=" + authInfo.DBPassword;

            DataBase db = new DataBase(sConnection, initialProviderType);
            db.ConnectionDetails.Token = authInfo.Token;

            return db;
        }

		/// <summary>
		/// Pulire le risorse in uso.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if (components != null) 
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Codice generato da Progettazione Windows Form
		/// <summary>
		/// Metodo necessario per il supporto della finestra di progettazione. Non modificare
		/// il contenuto del metodo con l'editor di codice.
		/// </summary>
		private void InitializeComponent()
		{
            this.label1 = new System.Windows.Forms.Label();
            this.txXmlText = new System.Windows.Forms.TextBox();
            this.btnList = new System.Windows.Forms.Button();
            this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
            this.btnOpen = new System.Windows.Forms.Button();
            this.txtFile = new System.Windows.Forms.TextBox();
            this.cmbCompLvl = new System.Windows.Forms.ComboBox();
            this.btnCmp = new System.Windows.Forms.Button();
            this.btnExpService = new System.Windows.Forms.Button();
            this.txtToken = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.label5 = new System.Windows.Forms.Label();
            this.txtIdComune = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.txtID = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.btnDetail = new System.Windows.Forms.Button();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(24, 12);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(152, 16);
            this.label1.TabIndex = 1;
            this.label1.Text = "Testo XML:";
            // 
            // txXmlText
            // 
            this.txXmlText.Location = new System.Drawing.Point(16, 36);
            this.txXmlText.MaxLength = 2000000;
            this.txXmlText.Multiline = true;
            this.txXmlText.Name = "txXmlText";
            this.txXmlText.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.txXmlText.Size = new System.Drawing.Size(744, 200);
            this.txXmlText.TabIndex = 2;
            // 
            // btnList
            // 
            this.btnList.Location = new System.Drawing.Point(684, 244);
            this.btnList.Name = "btnList";
            this.btnList.Size = new System.Drawing.Size(75, 24);
            this.btnList.TabIndex = 5;
            this.btnList.Text = "List";
            this.btnList.Click += new System.EventHandler(this.btnList_Click);
            // 
            // openFileDialog1
            // 
            this.openFileDialog1.Multiselect = true;
            // 
            // btnOpen
            // 
            this.btnOpen.Location = new System.Drawing.Point(656, 24);
            this.btnOpen.Name = "btnOpen";
            this.btnOpen.Size = new System.Drawing.Size(75, 23);
            this.btnOpen.TabIndex = 7;
            this.btnOpen.Text = "Open";
            this.btnOpen.Click += new System.EventHandler(this.btnOpen_Click);
            // 
            // txtFile
            // 
            this.txtFile.Location = new System.Drawing.Point(88, 24);
            this.txtFile.Name = "txtFile";
            this.txtFile.ReadOnly = true;
            this.txtFile.Size = new System.Drawing.Size(552, 20);
            this.txtFile.TabIndex = 8;
            // 
            // cmbCompLvl
            // 
            this.cmbCompLvl.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbCompLvl.Location = new System.Drawing.Point(128, 56);
            this.cmbCompLvl.Name = "cmbCompLvl";
            this.cmbCompLvl.Size = new System.Drawing.Size(216, 21);
            this.cmbCompLvl.TabIndex = 9;
            // 
            // btnCmp
            // 
            this.btnCmp.Location = new System.Drawing.Point(656, 56);
            this.btnCmp.Name = "btnCmp";
            this.btnCmp.Size = new System.Drawing.Size(75, 23);
            this.btnCmp.TabIndex = 10;
            this.btnCmp.Text = "Compress";
            this.btnCmp.Click += new System.EventHandler(this.btnCmp_Click);
            // 
            // btnExpService
            // 
            this.btnExpService.Location = new System.Drawing.Point(608, 56);
            this.btnExpService.Name = "btnExpService";
            this.btnExpService.Size = new System.Drawing.Size(96, 23);
            this.btnExpService.TabIndex = 19;
            this.btnExpService.Text = "Export Service";
            this.btnExpService.Click += new System.EventHandler(this.btnExpService_Click);
            // 
            // txtToken
            // 
            this.txtToken.Location = new System.Drawing.Point(72, 56);
            this.txtToken.Name = "txtToken";
            this.txtToken.Size = new System.Drawing.Size(288, 20);
            this.txtToken.TabIndex = 21;
            // 
            // label3
            // 
            this.label3.Location = new System.Drawing.Point(8, 60);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(40, 16);
            this.label3.TabIndex = 22;
            this.label3.Text = "Token";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.groupBox2);
            this.groupBox1.Controls.Add(this.label6);
            this.groupBox1.Controls.Add(this.label4);
            this.groupBox1.Controls.Add(this.txtFile);
            this.groupBox1.Controls.Add(this.btnOpen);
            this.groupBox1.Controls.Add(this.cmbCompLvl);
            this.groupBox1.Controls.Add(this.btnCmp);
            this.groupBox1.Location = new System.Drawing.Point(16, 280);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(744, 184);
            this.groupBox1.TabIndex = 23;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Compression";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.label5);
            this.groupBox2.Controls.Add(this.txtIdComune);
            this.groupBox2.Controls.Add(this.label2);
            this.groupBox2.Controls.Add(this.txtID);
            this.groupBox2.Controls.Add(this.label3);
            this.groupBox2.Controls.Add(this.txtToken);
            this.groupBox2.Controls.Add(this.btnExpService);
            this.groupBox2.Location = new System.Drawing.Point(16, 88);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(712, 88);
            this.groupBox2.TabIndex = 24;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Web Service";
            // 
            // label5
            // 
            this.label5.Location = new System.Drawing.Point(368, 60);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(64, 16);
            this.label5.TabIndex = 29;
            this.label5.Text = "Id Comune";
            // 
            // txtIdComune
            // 
            this.txtIdComune.Location = new System.Drawing.Point(432, 56);
            this.txtIdComune.Name = "txtIdComune";
            this.txtIdComune.Size = new System.Drawing.Size(68, 20);
            this.txtIdComune.TabIndex = 28;
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(524, 60);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(24, 16);
            this.label2.TabIndex = 27;
            this.label2.Text = "ID";
            // 
            // txtID
            // 
            this.txtID.Location = new System.Drawing.Point(556, 56);
            this.txtID.Name = "txtID";
            this.txtID.Size = new System.Drawing.Size(44, 20);
            this.txtID.TabIndex = 26;
            // 
            // label6
            // 
            this.label6.Location = new System.Drawing.Point(16, 60);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(104, 16);
            this.label6.TabIndex = 22;
            this.label6.Text = "Compression Level";
            // 
            // label4
            // 
            this.label4.Location = new System.Drawing.Point(16, 24);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(64, 16);
            this.label4.TabIndex = 21;
            this.label4.Text = "Files to zip";
            // 
            // btnDetail
            // 
            this.btnDetail.Location = new System.Drawing.Point(592, 244);
            this.btnDetail.Name = "btnDetail";
            this.btnDetail.Size = new System.Drawing.Size(75, 23);
            this.btnDetail.TabIndex = 24;
            this.btnDetail.Text = "Detail";
            this.btnDetail.Click += new System.EventHandler(this.btnDetail_Click);
            // 
            // Form1
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
            this.ClientSize = new System.Drawing.Size(776, 478);
            this.Controls.Add(this.btnDetail);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.txXmlText);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.btnList);
            this.Name = "Form1";
            this.Text = "Test";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

		}
		#endregion

		/// <summary>
		/// Il punto di ingresso principale dell'applicazione.
		/// </summary>
		[STAThread]
		static void Main() 
		{
			Application.Run(new Form1());
		}

		private void btnList_Click(object sender, System.EventArgs e)
		{
			try
			{
				WSSigeproExp.CWSSigeproExp pSgprExp = new WSSigeproExp.CWSSigeproExp();
				pSgprExp.Credentials = System.Net.CredentialCache.DefaultCredentials;
				pSgprExp.Timeout = Timeout.Infinite;
				//txXmlText.Text = pSgprExp.ListExp();
				MessageBox.Show("Lista delle esportazioni visualizzata correttamente","Info",MessageBoxButtons.OK,MessageBoxIcon.Information);
			}
			catch ( Exception ex )
			{
				string sMsg = ex.Message;
				if ( ex.InnerException!=null )
					sMsg += ex.InnerException.Message;

				MessageBox.Show(sMsg,"Error",MessageBoxButtons.OK,MessageBoxIcon.Error);
			}
		}

		private void btnOpen_Click(object sender, System.EventArgs e)
		{
			txtFile.Text = "";
			OpenFileDialog openFileDialog1 = new OpenFileDialog();

			openFileDialog1.InitialDirectory = "c:\\" ;
			openFileDialog1.Filter = "txt files (*.txt)|*.txt|All files (*.*)|*.*" ;
			openFileDialog1.FilterIndex = 1 ;
			openFileDialog1.RestoreDirectory = true ;
			openFileDialog1.CheckFileExists = false ;
			openFileDialog1.Multiselect = true ;

			if(openFileDialog1.ShowDialog() == DialogResult.OK)
			{
				filenames = openFileDialog1.FileNames;
				for ( int i=0; i<openFileDialog1.FileNames.Length; i++ )
					txtFile.Text += openFileDialog1.FileNames[i]+Path.PathSeparator;

				txtFile.Text = txtFile.Text.Remove(txtFile.Text.Length-1,1);
			}
		}

		private void btnCmp_Click(object sender, System.EventArgs e)
		{
			try
			{
				archive.SetLevel(Convert.ToInt32(cmbCompLvl.SelectedValue)); // 0 - store only to 9 - means best compression
		
				byte[] buffer = new byte[4096];
				
				foreach (string file in filenames) 
				{
					
					// Using GetFileName makes the result compatible with XP
					// as the resulting path is not absolute.
					ZipEntry entry = new ZipEntry(Path.GetFileName(file));
					
					// Setup the entry data as required.
					
					// Crc and size are handled by the library for seakable streams
					// so no need to do them here.

					// Could also use the last write time or similar for the file.
					entry.DateTime = DateTime.Now;
					archive.PutNextEntry(entry);
					
					using ( FileStream fs = File.OpenRead(file) ) 
					{
		
						// Using a fixed size buffer here makes no noticeable difference for output
						// but keeps a lid on memory usage.
						int sourceBytes;
						do 
						{
							sourceBytes = fs.Read(buffer, 0, buffer.Length);
							archive.Write(buffer, 0, sourceBytes);
						} while ( sourceBytes > 0 );
					}
				}

				// Finish/Close arent needed strictly as the using statement does this automatically
				
				// Finish is important to ensure trailing information for a Zip file is appended.  Without this
				// the created file would be invalid.
				archive.Finish();
				
				// Close is important to wrap things up and unlock the file.
				archive.Close();
	
				MessageBox.Show("Compressione eseguita correttamente","Info",MessageBoxButtons.OK,MessageBoxIcon.Information);
			}
			catch ( Exception ex )
			{
				MessageBox.Show(ex.Message,"Error",MessageBoxButtons.OK,MessageBoxIcon.Error);
			}
		}


		private void btnExpService_Click(object sender, System.EventArgs e)
		{
			try
			{
				WSSigeproExp.CWSSigeproExp pService = new WSSigeproExp.CWSSigeproExp();
				pService.Credentials = System.Net.CredentialCache.DefaultCredentials;
				pService.Timeout = Timeout.Infinite;
				
				Login pLgn = new Login();

                pLgn.Url = ExportTest.Properties.Settings.Default.ExportTest_WSSigeproLogin_Login;

                AuthenticationInfo authInfo = pLgn.GetTokenInfo(txtToken.Text, TipiAmbiente.DOTNET.ToString());

                if (authInfo == null)
                    throw new ApplicationException("Token non valido!!");

                Export.CExport exp = new Export.CExport();
                exp.FolderPath = ConfigurationSettings.AppSettings["FILE_PATH"].ToString();
                exp.ParametriCollection = null;
                exp.Token = txtToken.Text;
                exp.DataBase = CreateDatabase(authInfo);
                exp.DataBase.Connection.Open();
                exp.IdComune = authInfo.IdComune;
                exp.IdEsportazione = Convert.ToInt32(txtID.Text);
                exp.Zip = false;
                exp.Debug = Convert.ToBoolean(ConfigurationSettings.AppSettings["DEBUG"].ToString());

                byte[]  bytes = exp.Run(this.txXmlText.Text.Replace("\r\n", ""));


				MessageBox.Show("Esportazione terminata con successo","Info",MessageBoxButtons.OK,MessageBoxIcon.Information);
			}
			catch ( Exception ex )
			{
				string sMsg = ex.Message;
				if ( ex.InnerException!=null )
					sMsg += ex.InnerException.Message;

				MessageBox.Show(sMsg,"Error",MessageBoxButtons.OK,MessageBoxIcon.Error);
			}
		}

		private void btnDetail_Click(object sender, System.EventArgs e)
		{
			try
			{
				WSSigeproExp.CWSSigeproExp pSgprExp = new WSSigeproExp.CWSSigeproExp();
				pSgprExp.Credentials = System.Net.CredentialCache.DefaultCredentials;
				pSgprExp.Timeout = Timeout.Infinite;
				//txXmlText.Text = pSgprExp.GetEsportazione(1);
				MessageBox.Show("Dettagli dell'esportazioni visualizzati correttamente","Info",MessageBoxButtons.OK,MessageBoxIcon.Information);
			}
			catch ( Exception ex )
			{
				string sMsg = ex.Message;
				if ( ex.InnerException!=null )
					sMsg += ex.InnerException.Message;

				MessageBox.Show(sMsg,"Error",MessageBoxButtons.OK,MessageBoxIcon.Error);
			}
		}

	}

	public class CValCmb
	{
		private string sText ;
		private int iValue ;
    
		public  CValCmb(string sTxt, int iVal)
		{

			this.sText = sTxt;
			this.iValue = iVal;
		}

		public string Text
		{
			get
			{
				return sText;
			}
		}

		public int Value
		{
        
			get
			{
				return iValue ;
			}
		}
	}

    

    #region Enum per i tipi di ambiente
    public enum TipiAmbiente { DOTNET, JAVA, DEFAULT }
    #endregion

}
