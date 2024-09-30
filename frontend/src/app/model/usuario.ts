export interface Usuario {
  id: number;
  nome: string;
  email: string;
  dataCadastro: string;
  ativo: boolean;
  perfis: Perfil[];
}

interface Perfil {
  id: number;
  nome: string;
  descricao: string;
  permissoes: Permissao[];
}

interface Permissao {
  id: number;
  nome: string;
  descricao: string;
}
