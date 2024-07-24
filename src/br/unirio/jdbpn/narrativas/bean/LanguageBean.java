package br.unirio.jdbpn.narrativas.bean;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Scanner;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

@ManagedBean
@SessionScoped
//@ViewScoped
public class LanguageBean implements Serializable{

	private static final long serialVersionUID = 1L;	
	public static String Language = "en";
	
	private String titulo = "Appication to Support the SYP Method";
	private String menuArquivo = "My Projects";
	private String menuNovoProjeto = "New Project";
	private String menuSobre = "About";
	private String menuAjuda = "Help";
	private String menuAdministration = "Administration";
	private String menuNovoUsuario = "New User";
	private String menuAlterarUsuario = "Edit User";
	private String labelMeusProjetos = "My Projects";
	private String labelDescription = "Description";
	private String labelSenha = "Password";
	private String labelBtnLogin = "Log In";
	private String labelJoccon = "JoCCom - Complex Games Research Group";
	private String labelUnirio = "Federal University of State of Rio de Janeiro (UNIRIO)";
	private String labelPpgi = "Graduate Program in Informatics (PPGI)";
	private String labelBtnGerenciar = "Manage";
	private String labelManage = "Project Management";
	private String menuBPMN = "BPMN Diagram";
	private String menuPersonagens = "Characters";
	private String menuSentencas = "Sentences";
	private String menuCenas = "Scenes";
	private String menuAutores = "Project Authors";
	private String menuRoteiro = "Script";
	private String captionBPMN = "Importing BPMN Diagram";
	private String labelBtnSelecionar = "Select file...";
	private String labelEnviar = "Import";
	private String labelDica = "Tip";
	private String labelDicaBPMN = "Click above a BPMN element to view related sentences and scenes.";
	private String labelConfirmacao = "Confirmation";
	private String bpmnPerguntaConfirmacao = "Do you want to replace the BPMN diagram?";
	private String bpmnMsgConfirmacao = "All information that you recorded before will be lost.";
	private String labelSim = "Yes";
	private String labelNao = "No";
	private String bpmnDetalhesElemento = "Element details";
	private String bpmnSentencaCompleta = "Complete sentecen";
	private String labelTitulo = "Title";
	private String labelDescricaoCurta = "Short description";
	private String labelDescricao = "Description";
	private String labelPersonagens = "Characters";
	private String btnEditarCena = "Update scence";
	private String sentencaDica = "Click in a sentence to update it.";
	private String sentencaClmSentenca = "Sentença";
	private String sentencaClmBPMN = "BPMN Type";
	private String sentencaClmProxima = "Next";
	private String sentencaLblAjuste = "Sentence Adjustment";
	private String sentencaSujeito = "Subject";
	private String sentencaVerbo = "Verb";
	private String sentencaComplementos = "Complements";
	private String sentencaBtnAtualizar = "Update";
	private String sentencaLblCadastrada = "Recorded Sentences";
	private String authorLblAdicionar = "Add New Author";
	private String authorClmAutores = "Authors";
	private String authorBtnAdicionar = "Add";
	private String roteiroLabel = "Script";
	private String roteiroEscopo = "Type";
	private String roteiroBtnInk = "Create Ink Script";
	private String roteiroBtnTexto = "Create Text Script";
	private String roteiroConfiguracao = "Script Configuration";
	private String roteiroLblTexto = "Text";
	private String roteiroBtnImprimir = "Print";
	private String roteiroBtnPDF = "Export to PDF";
	private String menuIdioma = "Language";
	private String menuPortugues = "Portuguese";
	private String menuIngles = "English";
	private String personagemCadastrar = "Project Characters";
	private String personagemEditar = "Edit Character";
	private String personagemCasdastrados = "Characters";
	private String personagemBtnEditar = "Edit";
	private String personagemLblEditando = "Editing...";
	private String personagemNome = "Name";
	private String personagemFuncao = "Function";
	private String personagemArquetipo = "Archetype";
	private String personagemPhysic = "Physical traits";
	private String personagemPhysico = "Physiological traits";
	private String personagemObjetivo = "Goals";
	private String personagemBio = "Biography";
	private String personagemJornada = "Jorney";
	private String personagemBtnGravar = "Record";
	private String cenaCriarEditarApagar = "Create/Edit/Delete";
	private String cenaAdicional = "Additional Scene";
	private String cenaBtnAntes = "Before";
	private String cenaBtnDepois = "After";
	private String cenaConfirmacao = "Confirmation";
	private String cenaMsgConfirmacao = "Do not you will create the scene for this sentence? It is not possible to revert this change.";
	private String cenaCompor = "Scene Desing";
	private String cenaAnterior = "Previous Scene";
	private String cenaPosterior = "Post Scene";
	private String cenaDados = "Scene Data";
	private String cenaLocal = "Scene Place";
	private String cenaTipoLocal = "Place Type";
	private String cenaTempo = "Time";
	private String cenaPersonagemDisponivel = "Available";
	private String cenaPersonagemSelecionado = "Selected";
	private String cenaGatilhos = "Triggers";
	private String cenaResultados = "Outcomes";
	private String cenaAddDialogo = "+ Add Dialogs";
	private String cenaBtnGravar = "Record Scene";
	private String dialogoCena = "Dialogs";
	private String dialogoInserir = "Add New Dialog";
	private String dialogoIntroducao = "Introduction";
	private String dialogoPersonagem = "Character";
	private String dialogoObs = "Observation";
	private String dialogoFala = "Speach";
	private String dialogoNovo = "New Dialog";
	private String usuarioLabel = "Users";
	private String usuarioLabelNovo = "New User";
	private String usuarioNome = "User Name";
	private String usuarioPerfil = "Profile";
	private String usuarioBtnCadastrar = "Save";
	private String usuarioBtnLogin = "Log In";
	private String usuarioBtnAtualizar = "Update";
	
	
	
	public String getUsuarioLabel() {
		return usuarioLabel;
	}

	public void setUsuarioLabel(String usuarioLabel) {
		this.usuarioLabel = usuarioLabel;
	}

	public String getUsuarioLabelNovo() {
		return usuarioLabelNovo;
	}

	public void setUsuarioLabelNovo(String usuarioLabelNovo) {
		this.usuarioLabelNovo = usuarioLabelNovo;
	}

	public String getUsuarioNome() {
		return usuarioNome;
	}

	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
	}

	public String getUsuarioPerfil() {
		return usuarioPerfil;
	}

	public void setUsuarioPerfil(String usuarioPerfil) {
		this.usuarioPerfil = usuarioPerfil;
	}

	public String getUsuarioBtnCadastrar() {
		return usuarioBtnCadastrar;
	}

	public void setUsuarioBtnCadastrar(String usuarioBtnCadastrar) {
		this.usuarioBtnCadastrar = usuarioBtnCadastrar;
	}

	public String getUsuarioBtnLogin() {
		return usuarioBtnLogin;
	}

	public void setUsuarioBtnLogin(String usuarioBtnLogin) {
		this.usuarioBtnLogin = usuarioBtnLogin;
	}

	public String getUsuarioBtnAtualizar() {
		return usuarioBtnAtualizar;
	}

	public void setUsuarioBtnAtualizar(String usuarioBtnAtualizar) {
		this.usuarioBtnAtualizar = usuarioBtnAtualizar;
	}

	public String getDialogoCena() {
		return dialogoCena;
	}

	public void setDialogoCena(String dialogoCena) {
		this.dialogoCena = dialogoCena;
	}

	public String getDialogoInserir() {
		return dialogoInserir;
	}

	public void setDialogoInserir(String dialogoInserir) {
		this.dialogoInserir = dialogoInserir;
	}

	public String getDialogoIntroducao() {
		return dialogoIntroducao;
	}

	public void setDialogoIntroducao(String dialogoIntroducao) {
		this.dialogoIntroducao = dialogoIntroducao;
	}

	public String getDialogoPersonagem() {
		return dialogoPersonagem;
	}

	public void setDialogoPersonagem(String dialogoPersonagem) {
		this.dialogoPersonagem = dialogoPersonagem;
	}

	public String getDialogoObs() {
		return dialogoObs;
	}

	public void setDialogoObs(String dialogoObs) {
		this.dialogoObs = dialogoObs;
	}

	public String getDialogoFala() {
		return dialogoFala;
	}

	public void setDialogoFala(String dialogoFala) {
		this.dialogoFala = dialogoFala;
	}

	public String getDialogoNovo() {
		return dialogoNovo;
	}

	public void setDialogoNovo(String dialogoNovo) {
		this.dialogoNovo = dialogoNovo;
	}

	public String getCenaCompor() {
		return cenaCompor;
	}

	public void setCenaCompor(String cenaCompor) {
		this.cenaCompor = cenaCompor;
	}

	public String getCenaAnterior() {
		return cenaAnterior;
	}

	public void setCenaAnterior(String cenaAnterior) {
		this.cenaAnterior = cenaAnterior;
	}

	public String getCenaPosterior() {
		return cenaPosterior;
	}

	public void setCenaPosterior(String cenaPosterior) {
		this.cenaPosterior = cenaPosterior;
	}

	public String getCenaDados() {
		return cenaDados;
	}

	public void setCenaDados(String cenaDados) {
		this.cenaDados = cenaDados;
	}

	public String getCenaLocal() {
		return cenaLocal;
	}

	public void setCenaLocal(String cenaLocal) {
		this.cenaLocal = cenaLocal;
	}

	public String getCenaTipoLocal() {
		return cenaTipoLocal;
	}

	public void setCenaTipoLocal(String cenaTipoLocal) {
		this.cenaTipoLocal = cenaTipoLocal;
	}

	public String getCenaTempo() {
		return cenaTempo;
	}

	public void setCenaTempo(String cenaTempo) {
		this.cenaTempo = cenaTempo;
	}

	public String getCenaPersonagemDisponivel() {
		return cenaPersonagemDisponivel;
	}

	public void setCenaPersonagemDisponivel(String cenaPersonagemDisponivel) {
		this.cenaPersonagemDisponivel = cenaPersonagemDisponivel;
	}

	public String getCenaPersonagemSelecionado() {
		return cenaPersonagemSelecionado;
	}

	public void setCenaPersonagemSelecionado(String cenaPersonagemSelecionado) {
		this.cenaPersonagemSelecionado = cenaPersonagemSelecionado;
	}

	public String getCenaGatilhos() {
		return cenaGatilhos;
	}

	public void setCenaGatilhos(String cenaGatilhos) {
		this.cenaGatilhos = cenaGatilhos;
	}

	public String getCenaResultados() {
		return cenaResultados;
	}

	public void setCenaResultados(String cenaResultados) {
		this.cenaResultados = cenaResultados;
	}

	public String getCenaAddDialogo() {
		return cenaAddDialogo;
	}

	public void setCenaAddDialogo(String cenaAddDialogo) {
		this.cenaAddDialogo = cenaAddDialogo;
	}

	public String getCenaBtnGravar() {
		return cenaBtnGravar;
	}

	public void setCenaBtnGravar(String cenaBtnGravar) {
		this.cenaBtnGravar = cenaBtnGravar;
	}

	public String getCenaBtnAntes() {
		return cenaBtnAntes;
	}

	public void setCenaBtnAntes(String cenaBtnAntes) {
		this.cenaBtnAntes = cenaBtnAntes;
	}

	public String getCenaBtnDepois() {
		return cenaBtnDepois;
	}

	public void setCenaBtnDepois(String cenaBtnDepois) {
		this.cenaBtnDepois = cenaBtnDepois;
	}

	public String getCenaConfirmacao() {
		return cenaConfirmacao;
	}

	public void setCenaConfirmacao(String cenaConfirmacao) {
		this.cenaConfirmacao = cenaConfirmacao;
	}

	public String getCenaMsgConfirmacao() {
		return cenaMsgConfirmacao;
	}

	public void setCenaMsgConfirmacao(String cenaMsgConfirmacao) {
		this.cenaMsgConfirmacao = cenaMsgConfirmacao;
	}

	public String getCenaCriarEditarApagar() {
		return cenaCriarEditarApagar;
	}

	public void setCenaCriarEditarApagar(String cenaCriarEditarApagar) {
		this.cenaCriarEditarApagar = cenaCriarEditarApagar;
	}

	public String getCenaAdicional() {
		return cenaAdicional;
	}

	public void setCenaAdicional(String cenaAdicional) {
		this.cenaAdicional = cenaAdicional;
	}

	public String getPersonagemCadastrar() {
		return personagemCadastrar;
	}

	public void setPersonagemCadastrar(String personagemCadastrar) {
		this.personagemCadastrar = personagemCadastrar;
	}

	public String getPersonagemEditar() {
		return personagemEditar;
	}

	public void setPersonagemEditar(String personagemEditar) {
		this.personagemEditar = personagemEditar;
	}

	public String getPersonagemCasdastrados() {
		return personagemCasdastrados;
	}

	public void setPersonagemCasdastrados(String personagemCasdastrados) {
		this.personagemCasdastrados = personagemCasdastrados;
	}

	public String getPersonagemBtnEditar() {
		return personagemBtnEditar;
	}

	public void setPersonagemBtnEditar(String personagemBtnEditar) {
		this.personagemBtnEditar = personagemBtnEditar;
	}

	public String getPersonagemLblEditando() {
		return personagemLblEditando;
	}

	public void setPersonagemLblEditando(String personagemLblEditando) {
		this.personagemLblEditando = personagemLblEditando;
	}

	public String getPersonagemNome() {
		return personagemNome;
	}

	public void setPersonagemNome(String personagemNome) {
		this.personagemNome = personagemNome;
	}

	public String getPersonagemFuncao() {
		return personagemFuncao;
	}

	public void setPersonagemFuncao(String personagemFuncao) {
		this.personagemFuncao = personagemFuncao;
	}

	public String getPersonagemArquetipo() {
		return personagemArquetipo;
	}

	public void setPersonagemArquetipo(String personagemArquetipo) {
		this.personagemArquetipo = personagemArquetipo;
	}

	public String getPersonagemPhysic() {
		return personagemPhysic;
	}

	public void setPersonagemPhysic(String personagemPhysic) {
		this.personagemPhysic = personagemPhysic;
	}

	public String getPersonagemPhysico() {
		return personagemPhysico;
	}

	public void setPersonagemPhysico(String personagemPhysico) {
		this.personagemPhysico = personagemPhysico;
	}

	public String getPersonagemObjetivo() {
		return personagemObjetivo;
	}

	public void setPersonagemObjetivo(String personagemObjetivo) {
		this.personagemObjetivo = personagemObjetivo;
	}

	public String getPersonagemBio() {
		return personagemBio;
	}

	public void setPersonagemBio(String personagemBio) {
		this.personagemBio = personagemBio;
	}

	public String getPersonagemJornada() {
		return personagemJornada;
	}

	public void setPersonagemJornada(String personagemJornada) {
		this.personagemJornada = personagemJornada;
	}

	public String getPersonagemBtnGravar() {
		return personagemBtnGravar;
	}

	public void setPersonagemBtnGravar(String personagemBtnGravar) {
		this.personagemBtnGravar = personagemBtnGravar;
	}

	public String getMenuIdioma() {
		return menuIdioma;
	}

	public void setMenuIdioma(String menuIdioma) {
		this.menuIdioma = menuIdioma;
	}

	public String getMenuPortugues() {
		return menuPortugues;
	}

	public void setMenuPortugues(String menuPortugues) {
		this.menuPortugues = menuPortugues;
	}

	public String getMenuIngles() {
		return menuIngles;
	}

	public void setMenuIngles(String menuIngles) {
		this.menuIngles = menuIngles;
	}

	public String getRoteiroLblTexto() {
		return roteiroLblTexto;
	}

	public void setRoteiroLblTexto(String roteiroLblTexto) {
		this.roteiroLblTexto = roteiroLblTexto;
	}

	public String getRoteiroBtnImprimir() {
		return roteiroBtnImprimir;
	}

	public void setRoteiroBtnImprimir(String roteiroBtnImprimir) {
		this.roteiroBtnImprimir = roteiroBtnImprimir;
	}

	public String getRoteiroBtnPDF() {
		return roteiroBtnPDF;
	}

	public void setRoteiroBtnPDF(String roteiroBtnPDF) {
		this.roteiroBtnPDF = roteiroBtnPDF;
	}

	public String getRoteiroConfiguracao() {
		return roteiroConfiguracao;
	}

	public void setRoteiroConfiguracao(String roteiroConfiguracao) {
		this.roteiroConfiguracao = roteiroConfiguracao;
	}

	public String getRoteiroBtnTexto() {
		return roteiroBtnTexto;
	}

	public void setRoteiroBtnTexto(String roteiroBtnTexto) {
		this.roteiroBtnTexto = roteiroBtnTexto;
	}

	public String getAuthorLblAdicionar() {
		return authorLblAdicionar;
	}

	public String getRoteiroLabel() {
		return roteiroLabel;
	}

	public void setRoteiroLabel(String roteiroLabel) {
		this.roteiroLabel = roteiroLabel;
	}

	public String getRoteiroEscopo() {
		return roteiroEscopo;
	}

	public void setRoteiroEscopo(String roteiroEscopo) {
		this.roteiroEscopo = roteiroEscopo;
	}

	public String getRoteiroBtnInk() {
		return roteiroBtnInk;
	}

	public void setRoteiroBtnInk(String roteiroBtnInk) {
		this.roteiroBtnInk = roteiroBtnInk;
	}

	public void setAuthorLblAdicionar(String authorLblAdicionar) {
		this.authorLblAdicionar = authorLblAdicionar;
	}

	public String getAuthorClmAutores() {
		return authorClmAutores;
	}

	public void setAuthorClmAutores(String authorClmAutores) {
		this.authorClmAutores = authorClmAutores;
	}

	public String getAuthorBtnAdicionar() {
		return authorBtnAdicionar;
	}

	public void setAuthorBtnAdicionar(String authorBtnAdicionar) {
		this.authorBtnAdicionar = authorBtnAdicionar;
	}

	public String getSentencaLblCadastrada() {
		return sentencaLblCadastrada;
	}

	public void setSentencaLblCadastrada(String sentencaLblCadastrada) {
		this.sentencaLblCadastrada = sentencaLblCadastrada;
	}

	public String getSentencaBtnAtualizar() {
		return sentencaBtnAtualizar;
	}

	public void setSentencaBtnAtualizar(String sentencaBtnAtualizar) {
		this.sentencaBtnAtualizar = sentencaBtnAtualizar;
	}

	public String getLabelConfirmacao() {
		return labelConfirmacao;
	}

	public String getSentencaDica() {
		return sentencaDica;
	}

	public void setSentencaDica(String sentencaDica) {
		this.sentencaDica = sentencaDica;
	}

	public String getSentencaClmSentenca() {
		return sentencaClmSentenca;
	}

	public void setSentencaClmSentenca(String sentencaClmSentenca) {
		this.sentencaClmSentenca = sentencaClmSentenca;
	}

	public String getSentencaClmBPMN() {
		return sentencaClmBPMN;
	}

	public void setSentencaClmBPMN(String sentencaClmBPMN) {
		this.sentencaClmBPMN = sentencaClmBPMN;
	}

	public String getSentencaClmProxima() {
		return sentencaClmProxima;
	}

	public void setSentencaClmProxima(String sentencaClmProxima) {
		this.sentencaClmProxima = sentencaClmProxima;
	}

	public String getSentencaLblAjuste() {
		return sentencaLblAjuste;
	}

	public void setSentencaLblAjuste(String sentencaLblAjuste) {
		this.sentencaLblAjuste = sentencaLblAjuste;
	}

	public String getSentencaSujeito() {
		return sentencaSujeito;
	}

	public void setSentencaSujeito(String sentencaSujeito) {
		this.sentencaSujeito = sentencaSujeito;
	}

	public String getSentencaVerbo() {
		return sentencaVerbo;
	}

	public void setSentencaVerbo(String sentencaVerbo) {
		this.sentencaVerbo = sentencaVerbo;
	}

	public String getSentencaComplementos() {
		return sentencaComplementos;
	}

	public void setSentencaComplementos(String sentencaComplementos) {
		this.sentencaComplementos = sentencaComplementos;
	}

	public void setLabelConfirmacao(String labelConfirmacao) {
		this.labelConfirmacao = labelConfirmacao;
	}

	public String getBpmnPerguntaConfirmacao() {
		return bpmnPerguntaConfirmacao;
	}

	public void setBpmnPerguntaConfirmacao(String bpmnPerguntaConfirmacao) {
		this.bpmnPerguntaConfirmacao = bpmnPerguntaConfirmacao;
	}

	public String getBpmnMsgConfirmacao() {
		return bpmnMsgConfirmacao;
	}

	public void setBpmnMsgConfirmacao(String bpmnMsgConfirmacao) {
		this.bpmnMsgConfirmacao = bpmnMsgConfirmacao;
	}

	public String getLabelSim() {
		return labelSim;
	}

	public void setLabelSim(String labelSim) {
		this.labelSim = labelSim;
	}

	public String getLabelNao() {
		return labelNao;
	}

	public void setLabelNao(String labelNao) {
		this.labelNao = labelNao;
	}

	public String getBpmnDetalhesElemento() {
		return bpmnDetalhesElemento;
	}

	public void setBpmnDetalhesElemento(String bpmnDetalhesElemento) {
		this.bpmnDetalhesElemento = bpmnDetalhesElemento;
	}

	public String getBpmnSentencaCompleta() {
		return bpmnSentencaCompleta;
	}

	public void setBpmnSentencaCompleta(String bpmnSentencaCompleta) {
		this.bpmnSentencaCompleta = bpmnSentencaCompleta;
	}

	public String getLabelTitulo() {
		return labelTitulo;
	}

	public void setLabelTitulo(String labelTitulo) {
		this.labelTitulo = labelTitulo;
	}

	public String getLabelDescricaoCurta() {
		return labelDescricaoCurta;
	}

	public void setLabelDescricaoCurta(String labelDescricaoCurta) {
		this.labelDescricaoCurta = labelDescricaoCurta;
	}

	public String getLabelDescricao() {
		return labelDescricao;
	}

	public void setLabelDescricao(String labelDescricao) {
		this.labelDescricao = labelDescricao;
	}

	public String getLabelPersonagens() {
		return labelPersonagens;
	}

	public void setLabelPersonagens(String labelPersonagens) {
		this.labelPersonagens = labelPersonagens;
	}

	public String getBtnEditarCena() {
		return btnEditarCena;
	}

	public void setBtnEditarCena(String btnEditarCena) {
		this.btnEditarCena = btnEditarCena;
	}

	public String getLabelDica() {
		return labelDica;
	}

	public void setLabelDica(String labelDica) {
		this.labelDica = labelDica;
	}

	public String getLabelDicaBPMN() {
		return labelDicaBPMN;
	}

	public void setLabelDicaBPMN(String labelDicaBPMN) {
		this.labelDicaBPMN = labelDicaBPMN;
	}

	public String getCaptionBPMN() {
		return captionBPMN;
	}

	public void setCaptionBPMN(String captionBPMN) {
		this.captionBPMN = captionBPMN;
	}

	public String getLabelBtnSelecionar() {
		return labelBtnSelecionar;
	}

	public void setLabelBtnSelecionar(String labelBtnSelecionar) {
		this.labelBtnSelecionar = labelBtnSelecionar;
	}

	public String getLabelEnviar() {
		return labelEnviar;
	}

	public void setLabelEnviar(String labelEnviar) {
		this.labelEnviar = labelEnviar;
	}
	
	public String getLabelBtnGerenciar() {
		return labelBtnGerenciar;
	}

	public void setLabelBtnGerenciar(String labelBtnGerenciar) {
		this.labelBtnGerenciar = labelBtnGerenciar;
	}

	public String getLabelManage() {
		return labelManage;
	}

	public void setLabelManage(String labelManage) {
		this.labelManage = labelManage;
	}

	public String getMenuBPMN() {
		return menuBPMN;
	}

	public void setMenuBPMN(String menuBPMN) {
		this.menuBPMN = menuBPMN;
	}

	public String getMenuPersonagens() {
		return menuPersonagens;
	}

	public void setMenuPersonagens(String menuPersonagens) {
		this.menuPersonagens = menuPersonagens;
	}

	public String getMenuSentencas() {
		return menuSentencas;
	}

	public void setMenuSentencas(String menuSentencas) {
		this.menuSentencas = menuSentencas;
	}

	public String getMenuCenas() {
		return menuCenas;
	}

	public void setMenuCenas(String menuCenas) {
		this.menuCenas = menuCenas;
	}

	public String getMenuAutores() {
		return menuAutores;
	}

	public void setMenuAutores(String menuAutores) {
		this.menuAutores = menuAutores;
	}

	public String getMenuRoteiro() {
		return menuRoteiro;
	}

	public void setMenuRoteiro(String menuRoteiro) {
		this.menuRoteiro = menuRoteiro;
	}

	public String getLabelJoccon() {
		return labelJoccon;
	}

	public void setLabelJoccon(String labelJoccon) {
		this.labelJoccon = labelJoccon;
	}

	public String getLabelUnirio() {
		return labelUnirio;
	}

	public void setLabelUnirio(String labelUnirio) {
		this.labelUnirio = labelUnirio;
	}

	public String getLabelPpgi() {
		return labelPpgi;
	}

	public void setLabelPpgi(String labelPpgi) {
		this.labelPpgi = labelPpgi;
	}

	public String getLabelSenha() {
		return labelSenha;
	}

	public void setLabelSenha(String labelSenha) {
		this.labelSenha = labelSenha;
	}

	public String getLabelBtnLogin() {
		return labelBtnLogin;
	}

	public void setLabelBtnLogin(String labelBtnLogin) {
		this.labelBtnLogin = labelBtnLogin;
	}

	public LanguageBean() {
		
		LanguageBean.Language = this.verificarIdioma();
		
		if(LanguageBean.Language.equals("en")) {
			LoadEnglish();
		}else if(LanguageBean.Language.equals("pt")) {
			LoadPortuguese();
		}
	}
	
	@SuppressWarnings("finally")
	private String verificarIdioma() {
		String lang = "pt";
		
		try {
			InputStream directorioFile = this.getClass().getClassLoader().getResourceAsStream("META-INF/language.txt");
						
			Scanner scaner = new Scanner(directorioFile);
			String fileContent = (scaner.hasNext() ? scaner.next() : "");
			
			lang = fileContent;
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			return lang;
		}
	}
	
	private void LoadPortuguese() {
		this.titulo = "Aplicativo de Suporte ao Método SYP";		
		this.menuArquivo = "Meus Projetos";
		this.menuNovoProjeto = "Novo Projeto";
		this.menuSobre = "Sobre";
		this.menuAjuda = "Ajuda";
		this.menuAdministration = "Administração";
		this.menuNovoUsuario = "Novo Usuário";
		this.menuAlterarUsuario = "Alterar Usuário";
		this.labelMeusProjetos = "Meus Projetos";
		this.labelDescription = "Descrição";
		this.labelSenha = "Senha";
		this.labelBtnLogin = "Efeturar Login";
		this.labelJoccon = "JoCCom - Grupo de Pesquisa em Jogos Complexos";
		this.labelUnirio = "Universidade Federal do Estado do Rio de Janeiro (UNIRIO)";
		this.labelPpgi = "Programa de Pós Graduação em Informática (PPGI)";
		this.labelBtnGerenciar = "Gerenciar";
		this.labelManage = "Gerenciar Projeto";
		this.menuBPMN = "Diagrama BPMN";
		this.menuPersonagens = "Personagens";
		this.menuSentencas = "Sentenças";
		this.menuCenas = "Cenas";
		this.menuAutores = "Autores do Projeto";
		this.menuRoteiro = "Roteiro";
		this.captionBPMN = "Importar diagrama BPMN";
		this.labelBtnSelecionar = "Selecione...";
		this.labelEnviar = "Enviar";
		this.labelDica = "DICA";
		this.labelDicaBPMN = "Clique em um dos elementos do diagrama para obter a sentença e a cena associadas.";
		this.labelConfirmacao = "Confirmação";
		this.bpmnPerguntaConfirmacao = "Deseja substituir o diagrama BPMN?";
		this.bpmnMsgConfirmacao = "Toda a informação que você gravou antes sobre o BPMN será perdida.";
		this.labelSim = "Sim";
		this.labelNao = "Não";
		this.bpmnDetalhesElemento = "Detalhes do elemento";
		this.bpmnSentencaCompleta = "Sentença completa";
		this.labelTitulo = "Título";
		this.labelDescricaoCurta = "Breve descrição";
		this.labelDescricao = "Descrição";
		this.labelPersonagens = "Personagens";
		this.btnEditarCena = "Editar Cena";
		this.sentencaDica = "Clique na sentença abaixo para alterar.";
		this.sentencaClmSentenca = "Sentença";
		this.sentencaClmBPMN = "Tipo BPMN";
		this.sentencaClmProxima = "Próxima";
		this.sentencaLblAjuste = "Adjuste de Sentença";
		this.sentencaSujeito = "Sujeito";
		this.sentencaVerbo = "Verbo";
		this.sentencaComplementos = "Complementos";
		this.sentencaBtnAtualizar = "Atualizar";
		this.sentencaLblCadastrada = "Sentenças Cadastradas";
		this.authorLblAdicionar = "Adicionar Autor ao Projeto";
		this.authorClmAutores = "Autores Cadastrados";
		this.authorBtnAdicionar = "Adicionar";
		this.roteiroLabel = "Roteiro";
		this.roteiroEscopo = "Escopo";
		this.roteiroBtnInk = "Gerar Roteiro em Ink";
		this.roteiroBtnTexto = "Gerar Roteiro em Texto";
		this.roteiroConfiguracao = "Configuração do Roteiro";
		this.roteiroLblTexto = "Texto";
		this.roteiroBtnImprimir = "Imprimir Roteiro";
		this.roteiroBtnPDF = "Exportar como PDF";
		this.menuIdioma = "Idioma";
		this.menuPortugues = "Português";
		this.menuIngles = "Inglês";
		this.personagemCadastrar = "Cadastrar Personagem";
		this.personagemEditar = "Editar Personagem";
		this.personagemCasdastrados = "Personagens Cadastrados";
		this.personagemBtnEditar = "Editar";
		this.personagemLblEditando = "Editando...";
		this.personagemNome = "Nome";
		this.personagemFuncao = "Função";
		this.personagemArquetipo = "Arquétipo";
		this.personagemPhysic = "Características Físicas";
		this.personagemPhysico = "Características Psicológicas";
		this.personagemObjetivo = "Objetivos";
		this.personagemBio = "Biografia";
		this.personagemJornada = "Jornada";
		this.personagemBtnGravar = "Registrar";
		this.cenaCriarEditarApagar = "Criar/Modificar/Descartar";
		this.cenaAdicional = "Cena Adicional";
		this.cenaBtnAntes = "Antes";
		this.cenaBtnDepois = "Depois";
		this.cenaConfirmacao = "Confirmação";
		this.cenaMsgConfirmacao = "Você confirma que não será criada cena para esta sentença? Não será possível reverter essa escolha.";
		this.cenaCompor = "Compor Cena";
		this.cenaAnterior = "Cena Anterior";
		this.cenaPosterior = "Cena Posterior";
		this.cenaDados = "Dados da Cena";
		this.cenaLocal = "Local";
		this.cenaTipoLocal = "Tipo de local";
		this.cenaTempo = "Tempo";
		this.cenaPersonagemDisponivel = "Disponíveis";
		this.cenaPersonagemSelecionado = "Selecionados";
		this.cenaGatilhos = "Gatilhos";
		this.cenaResultados = "Resultados";
		this.cenaAddDialogo = "+ Adicionar Diálogos";
		this.cenaBtnGravar = "Cadastrar Cena";
		this.dialogoCena = "Dialógos";
		this.dialogoInserir = "Adicionar Novo Diálogo";
		this.dialogoIntroducao = "Introdução";
		this.dialogoPersonagem = "Personagem";
		this.dialogoObs = "Observações";
		this.dialogoFala = "Fala";
		this.dialogoNovo = "Novo Diálogo";
		this.usuarioLabel = "Usuários";
		this.usuarioLabelNovo = "Novo Usuário";
		this.usuarioNome = "Nome";
		this.usuarioPerfil = "Perfil";
		this.usuarioBtnCadastrar = "Gravar";
		this.usuarioBtnLogin = "Log In";
		this.usuarioBtnAtualizar = "Atualizar";
	}
	
	private void LoadEnglish() {
		this.titulo = "Appication to Support the SYP Method";
		this.menuArquivo = "My Projects";
		this.menuNovoProjeto = "New Project";
		this.menuSobre = "About";
		this.menuAjuda = "Help";
		this.menuAdministration = "Administration";
		this.menuNovoUsuario = "New User";
		this.menuAlterarUsuario = "Edit User";
		this.labelMeusProjetos = "My Projects";
		this.labelDescription = "Description";
		this.labelSenha = "Password";
		this.labelBtnLogin = "Log In";
		this.labelJoccon = "JoCCom - Complex Games Research Group";
		this.labelUnirio = "Federal University of State of Rio de Janeiro (UNIRIO)";
		this.labelPpgi = "Graduate Program in Informatics (PPGI)";
		this.labelBtnGerenciar = "Manage";
		this.labelManage = "Project Management";
		this.menuBPMN = "BPMN Diagram";
		this.menuPersonagens = "Characters";
		this.menuSentencas = "Sentences";
		this.menuCenas = "Scenes";
		this.menuAutores = "Project Authors";
		this.menuRoteiro = "Script";
		this.captionBPMN = "Importing BPMN Diagram";
		this.labelBtnSelecionar = "Select file...";
		this.labelEnviar = "Import";
		this.labelDica = "TIP";
		this.labelDicaBPMN = "Click above a BPMN element to view related sentences and scenes.";
		this.labelConfirmacao = "Confirmation";
		this.bpmnPerguntaConfirmacao = "Do you want to replace the BPMN diagram?";
		this.bpmnMsgConfirmacao = "All information that you recorded before will be lost.";
		this.labelSim = "Yes";
		this.labelNao = "No";
		this.bpmnDetalhesElemento = "Element details";
		this.bpmnSentencaCompleta = "Complete sentece";
		this.labelTitulo = "Title";
		this.labelDescricaoCurta = "Short description";
		this.labelDescricao = "Description";
		this.labelPersonagens = "Characters";
		this.btnEditarCena = "Update scence";
		this.sentencaDica = "Click in a sentence to update it.";
		this.sentencaClmSentenca = "Sentence";
		this.sentencaClmBPMN = "BPMN Type";
		this.sentencaClmProxima = "Next";
		this.sentencaLblAjuste = "Sentence Adjustment";
		this.sentencaSujeito = "Subject";
		this.sentencaVerbo = "Verb";
		this.sentencaComplementos = "Complements";
		this.sentencaBtnAtualizar = "Update";
		this.sentencaLblCadastrada = "Recorded Sentences";
		this.authorLblAdicionar = "Add New Author";
		this.authorClmAutores = "Authors";
		this.authorBtnAdicionar = "Add";
		this.roteiroLabel = "Script";
		this.roteiroEscopo = "Type";
		this.roteiroBtnInk = "Create Ink Script";
		this.roteiroBtnTexto = "Create Text Script";
		this.roteiroConfiguracao = "Script Configuration";
		this.roteiroLblTexto = "Text";
		this.roteiroBtnImprimir = "Print";
		this.roteiroBtnPDF = "Export to PDF";
		this.menuIdioma = "Language";
		this.menuPortugues = "Portuguese";
		this.menuIngles = "English";
		this.personagemCadastrar = "Project Characters";
		this.personagemEditar = "Edit Character";
		this.personagemCasdastrados = "Characters";
		this.personagemBtnEditar = "Edit";
		this.personagemLblEditando = "Editing...";
		this.personagemNome = "Name";
		this.personagemFuncao = "Function";
		this.personagemArquetipo = "Archetype";
		this.personagemPhysic = "Physical traits";
		this.personagemPhysico = "Physiological traits";
		this.personagemObjetivo = "Goals";
		this.personagemBio = "Biography";
		this.personagemJornada = "Jorney";
		this.personagemBtnGravar = "Record";
		this.cenaCriarEditarApagar = "Create/Edit/Delete";
		this.cenaAdicional = "Additional Scene";
		this.cenaBtnAntes = "Before";
		this.cenaBtnDepois = "After";
		this.cenaConfirmacao = "Confirmation";
		this.cenaMsgConfirmacao = "Do not you will create the scene for this sentence? It is not possible to revert this change.";
		this.cenaCompor = "Scene Desing";
		this.cenaAnterior = "Previous Scene";
		this.cenaPosterior = "Post Scene";
		this.cenaDados = "Scene Data";
		this.cenaLocal = "Scene Place";
		this.cenaTipoLocal = "Place Type";
		this.cenaTempo = "Time";
		this.cenaPersonagemDisponivel = "Available";
		this.cenaPersonagemSelecionado = "Selected";
		this.cenaGatilhos = "Triggers";
		this.cenaResultados = "Outcomes";
		this.cenaAddDialogo = "+ Add Dialogs";
		this.cenaBtnGravar = "Record Scene";
		this.dialogoCena = "Dialogs";
		this.dialogoInserir = "Add New Dialog";
		this.dialogoIntroducao = "Introduction";
		this.dialogoPersonagem = "Character";
		this.dialogoObs = "Observation";
		this.dialogoFala = "Speach";
		this.dialogoNovo = "New Dialog";
		this.usuarioLabel = "Users";
		this.usuarioLabelNovo = "New User";
		this.usuarioNome = "User Name";
		this.usuarioPerfil = "Profile";
		this.usuarioBtnCadastrar = "Save";
		this.usuarioBtnLogin = "Log In";
		this.usuarioBtnAtualizar = "Update";


	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getMenuArquivo() {
		return menuArquivo;
	}
	
	public void setMenuArquivo(String menuArquivo) {
		this.menuArquivo = menuArquivo;
	}
	
	public String getMenuNovoProjeto() {
		return menuNovoProjeto;
	}
	
	public void setMenuNovoProjeto(String menuNovoProjeto) {
		this.menuNovoProjeto = menuNovoProjeto;
	}
	
	public String getMenuAjuda() {
		return menuAjuda;
	}
	
	public void setMenuAjuda(String menuAjuda) {
		this.menuAjuda = menuAjuda;
	}
	
	public String getMenuSobre() {
		return menuSobre;
	}
	
	public void setMenuSobre(String menuSobre) {
		this.menuSobre = menuSobre;
	}
	
	public String getMenuAdministration() {
		return menuAdministration;
	}
	
	public void setMenuAdministration(String menuAdministration) {
		this.menuAdministration = menuAdministration;
	}
		
	public String getMenuNovoUsuario() {
		return menuNovoUsuario;
	}
	
	public void setMenuNovoUsuario(String menuNovoUsuario) {
		this.menuNovoUsuario = menuNovoUsuario;
	}
	
	public String getMenuAlterarUsuario() {
		return menuAlterarUsuario;
	}
	
	public void setMenuAlterarUsuario(String menuAlterarUsuario) {
		this.menuAlterarUsuario = menuAlterarUsuario;
	}
	
	public String getLabelMeusProjetos() {
		return labelMeusProjetos;
	}
	
	public void setLabelMeusProjetos(String labelMeusProjetos) {
		this.labelMeusProjetos = labelMeusProjetos;
	}
	
	public String getLabelDescription() {
		return labelDescription;
	}
	
	public void setLabelDescription(String labelDescription) {
		this.labelDescription = labelDescription;
	}
	
	public String mudarIdioma(int lang) {
		if(lang == 2) {
			LanguageBean.Language = "en";
			LoadEnglish();
		}else if(lang == 1) {
			LanguageBean.Language = "pt";
			LoadPortuguese();
		}
		
		return "inicio?faces-redirect=true";
	}
}