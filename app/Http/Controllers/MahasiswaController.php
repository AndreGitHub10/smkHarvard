<?php

namespace App\Http\Controllers;

use App\Models\Mahasiswa;
use App\Http\Requests\StoreMahasiswaRequest;
use App\Http\Requests\UpdateMahasiswaRequest;
use App\Http\Requests\DeleteMahasiswaRequest;

class MahasiswaController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $mahasiswa = Mahasiswa::all();
        return view('welcome', compact('mahasiswa'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */

    public function createMahasiswa(StoreMahasiswaRequest $request)
    {
        // $nama = 'andre';
        // $alamat = 'tamvan';
        $mahasiswa = new Mahasiswa;
        $mahasiswa->nama = $request->nama;
        $mahasiswa->nrp = $request->nrp;
        $mahasiswa->alamat = $request->alamat;
        $mahasiswa->prodi = $request->prodi;
        $mahasiswa->kontak = $request->kontak;
        $result = $mahasiswa->save();
        if($result){
            $response['error']=false;
            $response['message'] = 'Mahasiswa berhasil ditambahkan';
            $response['mahasiswa'] = Mahasiswa::all();
        }else{
            $response['error'] = true;
            $response['message'] = 'Some error';
        }
        return $response;
    }

    public function getMahasiswa()
    {
        $response['error'] = false;
        $response['message'] = 'Request successfully completed';
        $allMhs = Mahasiswa::all();
        $response['mahasiswa'] = json_decode(json_encode($allMhs), true);
        return $response;
    }

    public function updateMahasiswa(UpdateMahasiswaRequest $request)
    {
        if (isset($request->id)) {
            $mahasiswa = array();
            $mahasiswa['nama'] = $request->nama;
            $mahasiswa['nrp'] = $request->nrp;
            $mahasiswa['alamat'] = $request->alamat;
            $mahasiswa['prodi'] = $request->prodi;
            $mahasiswa['kontak'] = $request->kontak;
            $result = Mahasiswa::where('id', $request->id)->first()->update($mahasiswa);
            if($result){
                $response['error']=false;
                $response['message'] = 'Mahasiswa ' . $mahasiswa['nrp'] . ' berhasil diupdate';
                $response['mahasiswa'] = Mahasiswa::all();
            }else{
                $response['error'] = true;
                $response['message'] = 'Some error';
            }
        }
        return $response;
    }

    public function deleteMahasiswa(DeleteMahasiswaRequest $request)
    {
        if (isset($request->id)) {
            Mahasiswa::where('id', $request->id)->first()->delete();
            $response['error']=false;
            $response['message'] = 'Delete mahasiswa berhasil';
            $response['mahasiswa'] = Mahasiswa::all();
        } else {
            $response['error']=true;
            $response['message'] = 'Tidak ada id';
        }
        return $response;
    }

    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \App\Http\Requests\StoreMahasiswaRequest  $request
     * @return \Illuminate\Http\Response
     */
    public function store(StoreMahasiswaRequest $request)
    {
        //
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Models\Mahasiswa  $mahasiswa
     * @return \Illuminate\Http\Response
     */
    public function show(Mahasiswa $mahasiswa)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\Models\Mahasiswa  $mahasiswa
     * @return \Illuminate\Http\Response
     */
    public function edit(Mahasiswa $mahasiswa)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \App\Http\Requests\UpdateMahasiswaRequest  $request
     * @param  \App\Models\Mahasiswa  $mahasiswa
     * @return \Illuminate\Http\Response
     */
    public function update(UpdateMahasiswaRequest $request, Mahasiswa $mahasiswa)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Models\Mahasiswa  $mahasiswa
     * @return \Illuminate\Http\Response
     */
    public function destroy(Mahasiswa $mahasiswa)
    {
        //
    }
}
