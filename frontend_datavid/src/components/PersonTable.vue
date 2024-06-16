<template>
  <div class="card">
    <div class="table-header">
      <h2 class="table-title">Datavid Members</h2>
    </div>
    <DataTable :value="people" dataKey="id" scrollable scrollHeight="1000px" expandableRows>
      <Column field="firstName" header="First Name">
        <template #body="slotProps">
          {{ slotProps.data.firstName }}
        </template>
      </Column>

      <Column field="lastName" header="Last Name">
        <template #body="slotProps">
          {{ slotProps.data.lastName }}
        </template>
      </Column>

      <Column field="birthDate" header="Birth Date">
        <template #body="slotProps">
          <div :class="{ birthday: birthdayToday(slotProps.data.birthDate) }">
            {{ new Date(slotProps.data.birthDate).toLocaleDateString() }}
            <div v-if="birthdayToday(slotProps.data.birthDate)" class="birthday-text">Happy birthday! 🎁</div>
          </div>
        </template>
      </Column>

      <Column field="city" header="City">
        <template #body="slotProps">
          {{ slotProps.data.city }}
        </template>
      </Column>

      <Column field="country" header="Country">
        <template #body="slotProps">
          {{ slotProps.data.country }}
        </template>
      </Column>

      <Column header="Remove" bodyStyle="text-align: center;" headerStyle="width: 8em">
        <template #body="slotProps">
          <div class="button-container">
            <Button icon="pi pi-times" severity="danger" @click="deletePerson(slotProps.data.id)" rounded outlined aria-label="Cancel" />
          </div>
        </template>
      </Column>
    </DataTable>
  </div>
  <Toast />
</template>

<script>
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Button from 'primevue/button';
import PersonService from '@/services/person_service';
import Toast from 'primevue/toast';
import { useToast } from 'primevue/usetoast';

export default {
  components: {
    DataTable,
    Column,
    Button,
    Toast
  },
  data() {
    return {
      expandedRows: null,
      filters: {},
      people: [],
    };
  },
  
  setup() {
    const toast = useToast();
    function showSuccessDeletePersonMessage() {
      toast.add({
        severity: 'success',
        summary: 'Delete Successful',
        detail: 'Person deleted successfully!',
        life: 4000,
      });
    }
    return { showSuccessDeletePersonMessage };
  },
  methods: {
    refreshData() {
      this.getAllPeople();
    },
    deletePerson(personId) {
      PersonService.deletePerson(personId)
        .then(() => {
          this.refreshData();
          this.showSuccessDeletePersonMessage();
        }).catch(error => {
          console.log("Error deleting person!" + error);
        });
    },
    getAllPeople() {
      PersonService.getAllPeople()
        .then(response => {
          this.people = response.data.map(person => ({
            ...person,
            birthDate: new Date(person.birthDate).toISOString()
          }));
        }).catch(error => {
          console.log(error);
        });
    },
   
    birthdayToday(birthdate) {
      const today = new Date();
      const birthDate = new Date(birthdate);
      return today.getDate() === birthDate.getDate() && today.getMonth() === birthDate.getMonth();
    }
  },
  mounted() {
    this.getAllPeople();
  }
};
</script>

<style scoped>
.card {
  border-radius: 0.8rem;
  box-shadow: 2px 2px 2px 4px 0 rgba(0,0,0,0.1);
  margin: 1rem 0;
}

.p-datatable .p-column-header-content {
  display: flex;
  justify-content: center;
  align-items: center;
}

.p-datatable .p-datatable-tbody > tr > td {
  padding: 0.25rem !important;
  border-bottom: 1px solid #e0e0e0; 
  font-size: 0.9rem;
  text-align: center;
}

.p-datatable .p-datatable-thead > tr > th {
  color: rgb(0, 0, 0);
  text-align: center;
}

.p-datatable .p-datatable-tbody > tr:nth-child(odd) {
  background-color: #f9f9f9; 
  text-align: center;
}

.p-datatable .p-datatable-tbody > tr > td .p-datatable.p-component .p-datatable-tablewrapper {
  box-shadow: 0 2px 4px 0 rgba(0,0,0,0.1);
  margin: 1rem 0;
  text-align: center;
}

.p-button {
  border-radius: 0.25rem; 
  margin: 0 0.25rem;
}

.p-datatable .p-datatable-tbody > tr > td:last-child {
  border: 1px solid #e0e0e0;
}

.button-container {
  display: flex;
  align-items: center;
  justify-content: center;
}

.table-header {
  padding: 1rem; 
  background-color: #e4f3ff; 
  border-radius: 5px; 
  text-align: center;
}

.table-title {
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.birthday {
  background-color: #fff4cb;
  padding: 5px;
  border-radius: 5px;
}

.birthday-text {
  color: rgb(249, 128, 95);
  font-weight: bold;
}
</style>
