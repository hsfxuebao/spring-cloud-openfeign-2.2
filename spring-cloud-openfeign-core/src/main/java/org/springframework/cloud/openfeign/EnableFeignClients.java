/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.openfeign;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * Scans for interfaces that declare they are feign clients (via
 * {@link org.springframework.cloud.openfeign.FeignClient} <code>@FeignClient</code>).
 * Configures component scanning directives for use with
 * {@link org.springframework.context.annotation.Configuration}
 * <code>@Configuration</code> classes.
 *
 * @author Spencer Gibb
 * @author Dave Syer
 * @since 1.0
 */
// @EnableFeignClients注解用来启动FeignClient，以支持Feign。
// 该注解可以通过配置，扫描指定位置的@FeignClient注解声明的Feign客户端接口
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(FeignClientsRegistrar.class)
public @interface EnableFeignClients {

	// value和basePackage具有相同的功能，其中value是basePackage的别名
	// value和basePackage只能同时使用一个
	/**
	 * Alias for the {@link #basePackages()} attribute. Allows for more concise annotation
	 * declarations e.g.: {@code @ComponentScan("org.my.pkg")} instead of
	 * {@code @ComponentScan(basePackages="org.my.pkg")}.
	 * @return the array of 'basePackages'.
	 */
	// 为basePackages属性的别名，允许使用更简洁的书写方式。例如：@EnableFeignClients({"com.cd", "com.ad"})
	String[] value() default {};

	/**
	 * Base packages to scan for annotated components.
	 * <p>
	 * {@link #value()} is an alias for (and mutually exclusive with) this attribute.
	 * <p>
	 * Use {@link #basePackageClasses()} for a type-safe alternative to String-based
	 * package names.
	 * @return the array of 'basePackages'.
	 */
	// 设置自动扫描带有@FeignClient注解的基础包路径。例如 @EnableFeignClients(basePackages = {"com.cd", "com.ad"})
	String[] basePackages() default {};

	/**
	 * Type-safe alternative to {@link #basePackages()} for specifying the packages to
	 * scan for annotated components. The package of each class specified will be scanned.
	 * <p>
	 * Consider creating a special no-op marker class or interface in each package that
	 * serves no purpose other than being referenced by this attribute.
	 * @return the array of 'basePackageClasses'.
	 */
	// 该属性是basePackages属性的安全替代属性。该属性将扫描指定的每个类所在的包下面的所有被@FeignClient修饰的类；
	// 这需要考虑在每个包中创建一个特殊的标记类或接口，该类或接口除了被该属性引用外，没有其他用途
	Class<?>[] basePackageClasses() default {};

	/**
	 * A custom <code>@Configuration</code> for all feign clients. Can contain override
	 * <code>@Bean</code> definition for the pieces that make up the client, for instance
	 * {@link feign.codec.Decoder}, {@link feign.codec.Encoder}, {@link feign.Contract}.
	 *
	 * @see FeignClientsConfiguration for the defaults
	 * @return list of default configurations
	 */
	// 该属性用来自定义所有Feign客户端的配置，使用@Configuration进行配置。
	// 当然也可以为某一个Feign客户端进行配置。具体配置方法见@FeignClient的configuration属性。
	Class<?>[] defaultConfiguration() default {};

	/**
	 * List of classes annotated with @FeignClient. If not empty, disables classpath
	 * scanning.
	 * @return list of FeignClient classes
	 */
	// 设置由@FeignClient注解修饰的类列表。如果clients不是空数组，则不通过类路径自动扫描功能来加载FeignClient
	// 例如 @EnableFeignClients(clients = {SchedualService.class})
	// 上面代码中引入FeignClient客户端SchedualService，且也只引入该FeignClient客户端。
	Class<?>[] clients() default {};

}
