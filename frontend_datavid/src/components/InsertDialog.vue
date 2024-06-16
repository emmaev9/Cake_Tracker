<template>
  <Dialog header="Register a new member" v-model:visible="this.internalVisible" :modal="true" :style="{width: '35vw', content: 'center' }">
    <form @submit.prevent="submitForm">
        <div class="p-fluid">
          <div class="p-field">
            <InputText id="firstName" v-model="person.firstName" placeholder="First Name" required />
            <ErrorMessage name="firstName" class="error-feedback" />
          </div>

          <div class="p-field">
            <InputText id="lastName" v-model="person.lastName" placeholder="Last Name" required />
            <ErrorMessage name="lastName" class="error-feedback" />
          </div>
          
          <div class="p-field">
            <Calendar v-model="person.birthDate"  showIcon iconDisplay="input" placeholder="Birth Date" />
            <ErrorMessage name="birthDate" class="error-feedback" />
          </div>

          <div class="p-field">
            <InputText id="country" v-model="person.country" placeholder="Country" required />
            <ErrorMessage name="coutry" class="error-feedback" />
          </div>

          <div class="p-field">
            <InputText id="city" v-model="person.city" placeholder="City" required />
            <ErrorMessage name="city" class="error-feedback" />
          </div>
        </div>
        
        <div class="button-container">
          <Button label="Save" text raised type="submit"/>
        </div>
        <div class="error-feedback">{{errorMessage}}</div>
    </form> 
  </Dialog>
  <Toast />
</template>

<script>
import Dialog from 'primevue/dialog';
import InputText from 'primevue/inputtext';
import Button from 'primevue/button';
import Toast from 'primevue/toast';
import Calendar from 'primevue/calendar'
import { ErrorMessage} from 'vee-validate';
import { useToast } from 'primevue/usetoast';
import PersonService from '@/services/person_service';

export default{
  name: 'InsertDialog',
  components: {
    Dialog,
    InputText,
    Button,
    ErrorMessage,
    Toast,
    Calendar,
    
  },
  props:{
    visible: Boolean
  },
  setup() {
    const toast = useToast();
    function showSuccessMessage() {
      toast.add({
        severity: 'success',
        summary: 'Registration Successful',
        detail: 'Person registered successfully!',
        life: 4000,
      });
    }
   
    return { showSuccessMessage };
  },
  emits: ['update:visible', 'registeredPerson', 'savePerson'], 

  data() {
    return {
      internalVisible: this.visible,
      person: {
        firstName: '',
        lastName: '',
        city: '',
        country: '',
        birthDate: ''
      },
      errorMessage: ''
    }
  },
  methods: {
    async submitForm() {
      this.errorMessage = '';
      if(this.person.firstName === '' || this.person.lastName === '' || this.person.city === '' || this.person.country === '' || this.person.birthDate === ''){
        this.errorMessage = 'All fields are required!';
        return;
      }

      if(this.calculateAge(this.person.birthDate) < 18){
        this.errorMessage = 'Person must be at least 18 years old!';
        return;
      }
      
      PersonService.savePerson(this.person).then(() => {
        this.$emit('savePerson', this.person);
        console.log("Saving person1")
        this.showSuccessMessage();
        this.internalVisible = false;
      }, (error) => {
        console.log("Error saving person!"+ error);
        this.errorMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
      });
    },

    cancel1() {
      this.internalVisible = false;
    },
    
    calculateAge(birthdate){
      const today = new Date();
      const birthDate = new Date(birthdate);
      let age = today.getFullYear() - birthDate.getFullYear();
      const month = today.getMonth() - birthDate.getMonth();
      if (month < 0 || (month === 0 && today.getDate() < birthDate.getDate())) {
        age--;
      }
      return age;
    }, 
  
  },
  watch: {
    visible(newValue) {
      this.internalVisible = newValue;
    },
    internalVisible(newValue) {
      this.$emit('update:visible', newValue);
    }
  },
}

</script>
<style scoped>
.dialog-content {
  padding: 20px;
}

.button-container {
  display: flex;
  justify-content: center;
}

.p-field {
  margin-bottom: 10px; 
  padding: auto;
  margin-left: 7px;
  margin-right: 7px;
}

.error-feedback {
  color: #ff6161;
  font-size: 0.8rem;
  display: flex;
  justify-content: center;
}
</style>