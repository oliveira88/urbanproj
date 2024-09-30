import { Usuario } from './../model/usuario';
import { DatePipe, JsonPipe } from '@angular/common';
import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {
  MatNativeDateModule,
  provideNativeDateAdapter,
} from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ProjetoObra } from '../model';
import { ObraFormComponent } from '../obra-form/obra-form.component';
import { ObrasService } from '../services/obras.service';
import { MatRadioModule } from '@angular/material/radio';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manter-obra',
  standalone: true,
  imports: [
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatRadioModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatSnackBarModule,
    DatePipe,
    JsonPipe,
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './manter-obra.component.html',
  styleUrl: './manter-obra.component.scss',
})
export class ManterObraComponent implements OnInit {
  title = 'urbanweb';

  displayedColumns: string[] = [
    'id',
    'nome',
    'descricao',
    'responsavel',
    'bairro',
    'status',
    'dataInicio',
    'dataFim',
    'action',
  ];

  dataSource: MatTableDataSource<ProjetoObra> =
    new MatTableDataSource<ProjetoObra>([]);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  private obrasService = inject(ObrasService);
  private dialogRef = inject(MatDialog);
  private snackbar = inject(MatSnackBar);
  private router = inject(Router);
  usuario?: Usuario;
  ngOnInit() {
    this.obterObras();
    this.usuario = JSON.parse(localStorage.getItem('user') || '{}');
  }

  get canView() {
    return this.usuario?.perfis?.some(
      (perfil) =>
        ['Gestor'].includes(perfil.nome) &&
        perfil.permissoes.some((p) => p.nome === 'LER')
    );
  }

  get canCreate() {
    return this.usuario?.perfis?.some(
      (perfil) =>
        ['Gestor'].includes(perfil.nome) &&
        perfil.permissoes.some((p) => p.nome === 'CRIAR')
    );
  }

  get canEdit() {
    return this.usuario?.perfis?.some(
      (perfil) =>
        ['Gestor'].includes(perfil.nome) &&
        perfil.permissoes.some((p) => p.nome === 'ATUALIZAR')
    );
  }
  get canDelete() {
    return this.usuario?.perfis?.some(
      (perfil) =>
        ['Gestor'].includes(perfil.nome) &&
        perfil.permissoes.some((p) => p.nome === 'DELETAR')
    );
  }

  openEditForm(obj: ProjetoObra) {
    const data = {
      ...obj,
      responsavel: obj.responsavel.id,
      status: obj.status.id,
    };
    const dialogRef = this.dialogRef.open(ObraFormComponent, { data });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.obterObras();
      }
    });
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  deleteObra(id: number) {
    this.obrasService.deleteObra(id).subscribe(() => {
      this.snackbar.open('Obra deletada com sucesso', 'Fechar', {
        duration: 2000,
        verticalPosition: 'top',
      });
      this.obterObras();
    });
  }
  addObra() {
    const dialogRef = this.dialogRef.open(ObraFormComponent);

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.obterObras();
      }
    });
  }

  obterObras() {
    this.obrasService.getObras().subscribe((res) => {
      this.dataSource = new MatTableDataSource(res);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  logout() {
    localStorage.removeItem('user');
    this.router.navigate(['/'], { replaceUrl: true });
  }
}
