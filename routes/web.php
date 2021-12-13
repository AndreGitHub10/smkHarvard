<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\MahasiswaController;
/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

// Route::get('/', function () {
//     return ('welcome');
// });

Route::get('/', [MahasiswaController::class, 'index']);
Route::post('/create_mahasiswa', [MahasiswaController::class, 'createMahasiswa']);
Route::get('/get_mahasiswa', [MahasiswaController::class, 'getMahasiswa'])->name('getMahasiswa');
Route::post('/update_mahasiswa', [MahasiswaController::class, 'updateMahasiswa']);
Route::get('/delete_mahasiswa', [MahasiswaController::class, 'deleteMahasiswa'])->name('deleteMahasiswa');