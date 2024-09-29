import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { RouterOutlet } from '@angular/router';
import { ProjetoObra } from './model';
import { ObraFormComponent } from './obra-form/obra-form.component';
import { ObrasService } from './services/obras.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
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
