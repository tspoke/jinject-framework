package com.jinject.bind.impl;

import org.junit.Assert;
import org.junit.Test;

import com.jinject.bind.api.IBinder;
import com.jinject.bind.exception.BindingResolverException;
import com.jinject.bind.impl.Binder;
import com.jinject.utils.IModel;
import com.jinject.utils.Model;
import com.jinject.utils.MyEnum;
import com.jinject.utils.ShipModel;

public class BindingSystemTest {

	@Test
	public void bindingSimple() throws InstantiationException, IllegalAccessException, BindingResolverException{
		IBinder binder = new Binder();
		
		binder.bind(IModel.class).to(Model.class);
		@SuppressWarnings("unchecked")
		Class<Model> clazz = (Class<Model>) binder.getBinding(IModel.class);
		Model AA = clazz.newInstance();
		Assert.assertEquals(AA.getValue(), 0);
	}

	@Test
	public void bindingSimpleByValue() throws InstantiationException, IllegalAccessException, BindingResolverException{
		IBinder binder = new Binder();
		
		Model A = new Model();
		A.setValue(100);
		A.str = "A";
		
		binder.bind(IModel.class).to(A);

		Model AA = (Model) binder.getBinding(IModel.class);
		Assert.assertEquals(AA.getValue(), 100);
	}

	@Test
	public void bindingSimpleByValueByName() throws InstantiationException, IllegalAccessException, BindingResolverException{
		IBinder binder = new Binder();
		
		Model A = new Model();
		A.setValue(100);
		A.str = "A";
		
		binder.bind(IModel.class).to(A).toName("test");

		Model AA = (Model) binder.getBinding(IModel.class, "test");
		Assert.assertEquals(AA.getValue(), 100);
	}

	@Test(expected=BindingResolverException.class)
	public void bindingSimpleByValueByNameTryGotByDefault() throws InstantiationException, IllegalAccessException, BindingResolverException{
		IBinder binder = new Binder();
		
		Model A = new Model();
		A.setValue(100);
		A.str = "A";
		
		binder.bind(IModel.class).to(A).toName("test");
		binder.getBinding(IModel.class); // throw an exception because defaultValue IS NOT nameValue. It's a choice :)
	}
	
	@Test
	public void bindingByValueAndName() throws InstantiationException, IllegalAccessException, BindingResolverException{
		IBinder binder = new Binder();

		Model A = new Model();
		A.setValue(100);
		A.str = "A";

		Model B = new Model();
		B.setValue(200);
		B.str = "B";
		
		Model C = new Model();
		C.setValue(300);
		C.str = "C";
		
		binder.bind(IModel.class).to(A).toName("A");
		binder.bind(IModel.class).to(B).toName("B");
		binder.bind(IModel.class).to(C).toName("C");
		
		Model AA = (Model) binder.getBinding(IModel.class, "A");
		Model BB = (Model) binder.getBinding(IModel.class, "B");
		Model CC = (Model) binder.getBinding(IModel.class, "C");

		Assert.assertEquals(AA.getValue(), 100);
		Assert.assertEquals(BB.getValue(), 200);
		Assert.assertEquals(CC.getValue(), 300);
	}
	
	@Test
	public void bindingNameAndClass() throws InstantiationException, IllegalAccessException, BindingResolverException{
		IBinder binder = new Binder();

		Model A = new Model();
		A.setValue(100);
		A.str = "A";
		
		binder.bind(IModel.class).to(Model.class);
		binder.bind(IModel.class).to(A).toName("A");
		
		Model AA = (Model) binder.getBinding(IModel.class, "A");
		@SuppressWarnings("unchecked")
		Class<Model> clazz = (Class<Model>) binder.getBinding(IModel.class);
		Model BB = clazz.newInstance();

		Assert.assertEquals(AA.getValue(), 100);
		Assert.assertEquals(BB.getValue(), 0);

		Model CC = (Model) binder.getBinding(IModel.class, "A");
		Assert.assertEquals(CC.getValue(), 100);
	}
	

	@Test
	public void bindingMultipleImplementations() throws InstantiationException, IllegalAccessException, BindingResolverException{
		IBinder binder = new Binder();

		Model A = new Model();
		A.setValue(100);

		ShipModel B = new ShipModel();
		B.setValue(200);

		binder.bind(IModel.class).to(A).toName("test");
		binder.bind(IModel.class).to(B).toName("ship");
		binder.bind(IModel.class).to(B);

		Model AA = (Model) binder.getBinding(IModel.class, "test");
		ShipModel BB = (ShipModel) binder.getBinding(IModel.class, "ship");
		ShipModel CC = (ShipModel) binder.getBinding(IModel.class);
		
		Assert.assertEquals(AA.getValue(), 100);
		Assert.assertEquals(BB.getValue(), 200);
		Assert.assertEquals(CC.getValue(), 200);
	}


	@Test
	public void changeNamedBinding() throws InstantiationException, IllegalAccessException, BindingResolverException{
		IBinder binder = new Binder();

		Model A = new Model();
		A.setValue(100);
		A.str = "A";
		
		Model B = new Model();
		B.setValue(200);
		B.str = "B";

		binder.bind(IModel.class).to(A).toName("A");
		binder.bind(IModel.class).to(B).toName("A");
		
		Model BB = (Model) binder.getBinding(IModel.class, "A");

		Assert.assertEquals(BB.getValue(), 200);
	}

	@Test(expected=ExceptionInInitializerError.class)
	public void locked(){
		IBinder binder = new Binder();

		Model A = new Model();
		A.setValue(100);
		A.str = "A";

		binder.bind(IModel.class).to(A).toName("A").lock();
		binder.bind(IModel.class).to(ShipModel.class); // locked : exception
	}

	@Test
	public void bindByEnumToString() throws InstantiationException, IllegalAccessException, BindingResolverException{
		IBinder binder = new Binder();

		Model A = new Model();
		A.setValue(100);
		A.str = "A";

		binder.bind(IModel.class).to(A).toName(MyEnum.A.toString());
		
		Model BB = (Model) binder.getBinding(IModel.class, MyEnum.A.toString());
		Assert.assertEquals(BB.getValue(), 100);
	}

	@Test
	public void bindByEnum() throws InstantiationException, IllegalAccessException, BindingResolverException {
		
	}
}
