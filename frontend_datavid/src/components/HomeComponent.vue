<template>
  <div class="home-admin">
    <br>
    <br> 
    <h1 class="home-title">Welcome to Datavid Cake Tracker</h1>
    <div class="buttons-container">
      <Button label="Register member" outlined  @click="showPersonRegisterDialog" />
    </div>
    <InsertDialog :visible="visiblePersonDialog" @update:visible="this.updatePersonDialogVisibility" @savePerson="this.registerPerson"/>
    
    <div class="users-table">
      <PersonTable ref="personTable" /> 
    </div>

  </div>
</template>

<script>
import Button from 'primevue/button'
import PersonTable from '../components/PersonTable.vue'
import InsertDialog from '../components/InsertDialog.vue'

export default {
  name: 'HomeComponent',
  
  components: {
    PersonTable,
    Button,
    InsertDialog
  },
  data (){
    return{
      visiblePersonDialog: false,
    }
  },
  methods:{
    showPersonRegisterDialog(){
      this.visiblePersonDialog = true;
    },
    
    updatePersonDialogVisibility(newValue){
      this.visiblePersonDialog = newValue;
    },
   
    registerPerson() {
      this.$refs.personTable.refreshData();
    }
  }
}
</script>

<style scoped>
.buttons-container {
  display: flex;
  justify-content: center;
  gap: 10px; 
}

.users-table {
  margin-top: 20px;
  margin-left: 80px;
  margin-right: 80px;
  color:black;
}
.home-title{
  color: rgb(78, 170, 250);
}

</style>