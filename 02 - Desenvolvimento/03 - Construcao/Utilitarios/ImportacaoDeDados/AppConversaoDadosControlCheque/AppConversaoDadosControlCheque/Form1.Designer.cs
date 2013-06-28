namespace AppConversaoDadosControlCheque
{
    partial class Form1
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
            this.btn_ImportarBanco = new System.Windows.Forms.Button();
            this.btn_ImportarCliente = new System.Windows.Forms.Button();
            this.btn_Importar_Cheque = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // btn_ImportarBanco
            // 
            this.btn_ImportarBanco.Font = new System.Drawing.Font("Arial", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_ImportarBanco.Location = new System.Drawing.Point(51, 36);
            this.btn_ImportarBanco.Name = "btn_ImportarBanco";
            this.btn_ImportarBanco.Size = new System.Drawing.Size(197, 36);
            this.btn_ImportarBanco.TabIndex = 0;
            this.btn_ImportarBanco.Text = " Importar tabela banco";
            this.btn_ImportarBanco.UseVisualStyleBackColor = true;
            this.btn_ImportarBanco.Click += new System.EventHandler(this.button1_Click);
            // 
            // btn_ImportarCliente
            // 
            this.btn_ImportarCliente.Font = new System.Drawing.Font("Arial", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_ImportarCliente.Location = new System.Drawing.Point(51, 100);
            this.btn_ImportarCliente.Name = "btn_ImportarCliente";
            this.btn_ImportarCliente.Size = new System.Drawing.Size(197, 35);
            this.btn_ImportarCliente.TabIndex = 1;
            this.btn_ImportarCliente.Text = "Importar Tabela Cliente";
            this.btn_ImportarCliente.UseVisualStyleBackColor = true;
            this.btn_ImportarCliente.Click += new System.EventHandler(this.btn_ImportarCliente_Click);
            // 
            // btn_Importar_Cheque
            // 
            this.btn_Importar_Cheque.Font = new System.Drawing.Font("Arial", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_Importar_Cheque.Location = new System.Drawing.Point(51, 175);
            this.btn_Importar_Cheque.Name = "btn_Importar_Cheque";
            this.btn_Importar_Cheque.Size = new System.Drawing.Size(197, 35);
            this.btn_Importar_Cheque.TabIndex = 2;
            this.btn_Importar_Cheque.Text = "Importar Tabela Cheque";
            this.btn_Importar_Cheque.UseVisualStyleBackColor = true;
            this.btn_Importar_Cheque.Click += new System.EventHandler(this.btn_Importar_Cheque_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(292, 266);
            this.Controls.Add(this.btn_Importar_Cheque);
            this.Controls.Add(this.btn_ImportarCliente);
            this.Controls.Add(this.btn_ImportarBanco);
            this.Name = "Form1";
            this.Text = "Importador Control Cheque";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button btn_ImportarBanco;
        private System.Windows.Forms.Button btn_ImportarCliente;
        private System.Windows.Forms.Button btn_Importar_Cheque;
    }
}

