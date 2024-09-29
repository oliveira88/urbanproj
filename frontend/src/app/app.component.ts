import { ObrasService } from './services/obras.service';
import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import {
  MAT_DATE_FORMATS,
  MAT_DATE_LOCALE,
  MatNativeDateModule,
  provideNativeDateAdapter,
} from '@angular/material/core';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { ReactiveFormsModule } from '@angular/forms';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { DatePipe } from '@angular/common';
import { ObraFormComponent } from './obra-form/obra-form.component';
import { ProjetoObra } from './model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatSnackBarModule,
    DatePipe,
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
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
  ngOnInit() {
    this.obterObras();
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
}
function provideMomentDateAdapter(
  MY_FORMATS: any
): import('@angular/core').Provider {
  throw new Error('Function not implemented.');
}
