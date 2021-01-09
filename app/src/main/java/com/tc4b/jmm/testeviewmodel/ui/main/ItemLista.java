package com.tc4b.jmm.testeviewmodel.ui.main;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.constraintlayout.widget.Placeholder;

import java.security.PublicKey;
import java.util.List;
import java.util.Objects;

public class ItemLista implements Parcelable {
    int id;
    String nome;
    String grupo;

    public ItemLista(int id, String nome, String grupo) {
        this.id = id;
        this.nome = nome;
        this.grupo = grupo;
    }

    protected ItemLista(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        grupo = in.readString();
    }

    public static final Creator<ItemLista> CREATOR = new Creator<ItemLista>() {
        @Override
        public ItemLista createFromParcel(Parcel in) {
            return new ItemLista(in);
        }

        @Override
        public ItemLista[] newArray(int size) {
            return new ItemLista[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemLista)) return false;
        ItemLista itemLista = (ItemLista) o;
        return id == itemLista.id &&
                Objects.equals(nome, itemLista.nome) &&
                Objects.equals(grupo, itemLista.grupo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, grupo);
    }

    @Override
    public String toString() {
        return "ItemLista{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", grupo='" + grupo + '\'' +
                '}';
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeString(grupo);
    }

    public static class ListOfItemList implements Parcelable {
        int index;
        List<ItemLista> listaList;

        public ListOfItemList(int index, List<ItemLista> listaList) {
            this.index = index;
            this.listaList = listaList;
        }

        protected ListOfItemList(Parcel in) {
            index = in.readInt();
            listaList = in.createTypedArrayList(ItemLista.CREATOR);
        }

        public static final Creator<ListOfItemList> CREATOR = new Creator<ListOfItemList>() {
            @Override
            public ListOfItemList createFromParcel(Parcel in) {
                return new ListOfItemList(in);
            }

            @Override
            public ListOfItemList[] newArray(int size) {
                return new ListOfItemList[size];
            }
        };

        /**
         * Describe the kinds of special objects contained in this Parcelable
         * instance's marshaled representation. For example, if the object will
         * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
         * the return value of this method must include the
         * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
         *
         * @return a bitmask indicating the set of special object types marshaled
         * by this Parcelable object instance.
         */
        @Override
        public int describeContents() {
            return 0;
        }

        /**
         * Flatten this object in to a Parcel.
         *
         * @param dest  The Parcel in which the object should be written.
         * @param flags Additional flags about how the object should be written.
         *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
         */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(index);
            dest.writeParcelableList(listaList, flags);
        }
    }
}
