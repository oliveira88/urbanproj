import { Responsavel } from './responsavel';
import { Status } from './status';

export interface ProjetoObra {
  id: number;
  responsavel: Responsavel;
  status: Status;
  nome: string;
  descricao: string;
  bairro: string;
  dataInicio: Date;
  dataFim?: Date;
}
