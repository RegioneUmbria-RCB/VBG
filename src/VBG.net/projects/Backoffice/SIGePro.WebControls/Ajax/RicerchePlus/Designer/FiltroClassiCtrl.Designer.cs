namespace SIGePro.WebControls.Ajax.RicerchePlus.Designer
{
	partial class FiltroClassiCtrl
	{
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.IContainer components = null;

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		/// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
		protected override void Dispose(bool disposing)
		{
			if (disposing && (components != null))
			{
				components.Dispose();
			}
			base.Dispose(disposing);
		}

		#region Windows Form Designer generated code

		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.label1 = new System.Windows.Forms.Label();
			this.label2 = new System.Windows.Forms.Label();
			this.txtFilter = new System.Windows.Forms.TextBox();
			this.cmbClassi = new System.Windows.Forms.ComboBox();
			this.cmdOk = new System.Windows.Forms.Button();
			this.cmdClose = new System.Windows.Forms.Button();
			this.SuspendLayout();
			// 
			// label1
			// 
			this.label1.AutoSize = true;
			this.label1.Location = new System.Drawing.Point(2, 5);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(75, 13);
			this.label1.TabIndex = 0;
			this.label1.Text = "Parola chiave:";
			// 
			// label2
			// 
			this.label2.AutoSize = true;
			this.label2.Location = new System.Drawing.Point(2, 29);
			this.label2.Name = "label2";
			this.label2.Size = new System.Drawing.Size(38, 13);
			this.label2.TabIndex = 1;
			this.label2.Text = "Classi:";
			// 
			// txtFilter
			// 
			this.txtFilter.Location = new System.Drawing.Point(84, 2);
			this.txtFilter.Name = "txtFilter";
			this.txtFilter.Size = new System.Drawing.Size(348, 21);
			this.txtFilter.TabIndex = 0;
			this.txtFilter.TextChanged += new System.EventHandler(this.txtFilter_TextChanged);
			// 
			// cmbClassi
			// 
			this.cmbClassi.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
			this.cmbClassi.FormattingEnabled = true;
			this.cmbClassi.Location = new System.Drawing.Point(84, 26);
			this.cmbClassi.Name = "cmbClassi";
			this.cmbClassi.Size = new System.Drawing.Size(348, 21);
			this.cmbClassi.TabIndex = 1;
			// 
			// cmdOk
			// 
			this.cmdOk.Location = new System.Drawing.Point(3, 55);
			this.cmdOk.Name = "cmdOk";
			this.cmdOk.Size = new System.Drawing.Size(75, 23);
			this.cmdOk.TabIndex = 2;
			this.cmdOk.Text = "Ok";
			this.cmdOk.UseVisualStyleBackColor = true;
			this.cmdOk.Click += new System.EventHandler(this.cmdOk_Click);
			// 
			// cmdClose
			// 
			this.cmdClose.DialogResult = System.Windows.Forms.DialogResult.Cancel;
			this.cmdClose.Location = new System.Drawing.Point(84, 55);
			this.cmdClose.Name = "cmdClose";
			this.cmdClose.Size = new System.Drawing.Size(75, 23);
			this.cmdClose.TabIndex = 3;
			this.cmdClose.Text = "Chiudi";
			this.cmdClose.UseVisualStyleBackColor = true;
			this.cmdClose.Click += new System.EventHandler(this.cmdClose_Click);
			// 
			// FiltroClassiCtrl
			// 
			this.AcceptButton = this.cmdOk;
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.CancelButton = this.cmdClose;
			this.ClientSize = new System.Drawing.Size(434, 84);
			this.Controls.Add(this.cmdClose);
			this.Controls.Add(this.cmdOk);
			this.Controls.Add(this.cmbClassi);
			this.Controls.Add(this.txtFilter);
			this.Controls.Add(this.label2);
			this.Controls.Add(this.label1);
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
			this.MaximizeBox = false;
			this.MinimizeBox = false;
			this.Name = "FiltroClassiCtrl";
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
			this.Text = "Filtro classi";
			this.Load += new System.EventHandler(this.FiltroClassiCtrl_Load);
			this.ResumeLayout(false);
			this.PerformLayout();

		}

		#endregion

		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.Label label2;
		private System.Windows.Forms.TextBox txtFilter;
		private System.Windows.Forms.ComboBox cmbClassi;
		private System.Windows.Forms.Button cmdOk;
		private System.Windows.Forms.Button cmdClose;
	}
}