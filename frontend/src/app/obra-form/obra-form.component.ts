import { ObrasService } from './../services/obras.service';
import { Component, Inject, OnInit } from '@angular/core';
import {
  Form,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ResponsavelService } from '../services/responsavel.service';
import { Observable } from 'rxjs';
import { AsyncPipe, JsonPipe } from '@angular/common';

@Component({
  selector: 'app-obra-form',
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
    AsyncPipe,
    JsonPipe,
  ],
  templateUrl: './obra-form.component.html',
  styleUrl: './obra-form.component.scss',
})
export class ObraFormComponent implements OnInit {
  obraForm: FormGroup;
  responsaveis$!: Observable<any>;
  status$!: Observable<any>;
  constructor(
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<ObraFormComponent>,

    private snackbar: MatSnackBar,
    private obrasService: ObrasService,
    private responsavelService: ResponsavelService
  ) {
    this.obraForm = this.fb.group({
      id: [{ value: null, disabled: true }],
      nome: [''],
      descricao: [''],
      responsavel: [''],
      bairro: [''],
      status: [''],
      dataInicio: [''],
      dataFim: [''],
    });
  }

  ngOnInit() {
    if (this.data) {
      this.obraForm.patchValue(this.data);
    }
    this.obterResponsaveis();
    this.obterStatus();
  }

  onFormSubmit() {
    if (this.obraForm.valid && this.data) {
      this.obrasService
        .updateObra(this.obraForm.getRawValue())
        .subscribe(() => {
          this.snackbar.open('Obra atualizada com sucesso', 'Fechar', {
            duration: 2000,
            verticalPosition: 'top',
          });
          this.dialogRef.close(true);
        });
    } else {
      this.obrasService.createObra(this.obraForm.value).subscribe(() => {
        this.snackbar.open('Obra criada com sucesso', 'Fechar', {
          duration: 2000,
          verticalPosition: 'top',
        });
        this.dialogRef.close(true);
      });
    }
  }

  obterResponsaveis() {
    this.responsaveis$ = this.responsavelService.getResponsaveis();
  }
  obterStatus() {
    this.status$ = this.obrasService.obterStatus();
  }
}
